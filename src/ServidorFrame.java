import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ServidorFrame {
	static ObjectOutputStream saida;

	static Chat chat = new Chat("Servidor");
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		chat.send_btn.addActionListener(new ActionListener() {
			//adiciona um evento ao botão que, ao ser clicado, vai enviar a mensagem contida em msg_text
			@Override
			public void actionPerformed(ActionEvent e) {
				try {
					saida.writeObject(chat.msg_text.getText());
					saida.flush();
					chat.msg_area.append("\nServidor>>"+chat.msg_text.getText());
					chat.msg_text.setText("");//limpa a caixa de texto apos o envio
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});

		try {
			ServerSocket server = new ServerSocket(8522);// define o servidor socket utilizado
			//System.out.println(InetAddress.getLocalHost());//pega o endereço de rede, para conectar com outro computador da rede
			chat.msg_area.append("iniciado");
			while (true) {// para rodar constantemente as requisições
				Socket cliente = server.accept();// aguarda que o cliente conecte do outro lado da porta
				chat.msg_area.append("\nConectado " + cliente.getInetAddress());
				saida = new ObjectOutputStream(cliente.getOutputStream());
				saida.writeObject("Bem vindo, como posso ajudar?");

				ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
				String mensagem = "";
				do {// fica aqui ate o cliente enviar a mensagem "fim" obtendo a mensagem enviada pelo cliente
					mensagem = (String) entrada.readObject();
					chat.msg_area.append("\nCliente>> " + mensagem);
				} while (!mensagem.equalsIgnoreCase("FIM"));
				entrada.close();// se não fechar, vai dar um erro de conetion reset, pois ele vai estar buscando uma entrada de um cliente que não existe
				cliente.close();
				saida.close();
				chat.msg_area.append("\nfinalizada conexão");
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			chat.msg_area.append("\nErro: " + e.getMessage());

		}
		chat.msg_area.append("\nfinalizado server");
	}
}
