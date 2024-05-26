import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class Chat {
    JFrame serverFrame;
    JTextArea msg_area;
    JTextField msg_text;
    JButton send_btn;

	private class TecladoAdapter extends KeyAdapter{
		@Override
		public void keyReleased(KeyEvent e){// define que um evento vai ocorrer quando o usuario soltar a tecla
            int codigo_tecla=e.getKeyCode();
            if(codigo_tecla==KeyEvent.VK_ENTER){
                send_btn.doClick();//simula o evento de click no botao
            }  
		}
	}

    public Chat(String nome){
        serverFrame = new JFrame("Chat - "+nome);
        serverFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        serverFrame.setResizable(false);
        serverFrame.setSize(600, 500);
        serverFrame.setLocationRelativeTo(null);
        serverFrame.setLayout(new BorderLayout());

        msg_area = new JTextArea();
        msg_area.setEditable(false);// não permite que o usuario edite a area onde aparecem as mensagens
        JScrollPane scrollPane = new JScrollPane(msg_area);
        serverFrame.add(scrollPane, BorderLayout.CENTER);

        JPanel inputPanel = new JPanel(new BorderLayout());
        msg_text = new JTextField();
        inputPanel.add(msg_text, BorderLayout.CENTER);
        
        send_btn = new JButton("Enviar");
        inputPanel.add(send_btn, BorderLayout.EAST);

        serverFrame.add(inputPanel, BorderLayout.SOUTH);
        serverFrame.setVisible(true);
        msg_text.addKeyListener(new TecladoAdapter());
    }
}
