import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import javax.swing.JOptionPane;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ClienteFrame {
	static ObjectOutputStream saida;

	static Chat chat = new Chat("Cliente");
	public static void main(String[] args){

		// caso o cliente tente sair do chat sem finalizar a conexão
        chat.serverFrame.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int confirma = JOptionPane.showOptionDialog(
						chat.serverFrame,
                        "Tem certeza que deseja finalizar a janela?",
                        "Confirmação",
                        JOptionPane.YES_NO_OPTION,
                        JOptionPane.QUESTION_MESSAGE,
                        null,
                        null,
                        null);

                if (confirma == JOptionPane.YES_OPTION) {
					chat.msg_text.setText("FIM");
                    chat.serverFrame.dispose();
					chat.send_btn.doClick();
                }
            }
        });
		
		chat.send_btn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				// define que um evento ocorre ao apertar o botão
				try {
					saida.writeObject(chat.msg_text.getText());
					saida.flush();
					chat.msg_area.append("\nCliente>> "+chat.msg_text.getText());
					chat.msg_text.setText("");//limpa a caixa de texto apos o envio
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				
			}
		});
		try {
			Socket cliente=new Socket("127.0.0.1",8522);//com quem e em qual porta vai conectar
			chat.msg_area.append("Cliente conectado");
			ObjectInputStream entrada = new ObjectInputStream(cliente.getInputStream());
			saida = new ObjectOutputStream(cliente.getOutputStream());
			chat.msg_area.append("\nQuando finalizar a sessão, escreva 'fim' no terminal");
		    String mensagem;
		    do {
            	mensagem = (String) entrada.readObject();
				chat.msg_area.append("\nServidor>> " + mensagem);
			} while (!mensagem.equalsIgnoreCase("FIM"));	    
		} catch (Exception e) {
			// TODO: handle exception
			chat.msg_area.append("\nErro cliente: "+e.getMessage());
		}
		chat.msg_area.append("\nfinalizado cliente");
	}

}
