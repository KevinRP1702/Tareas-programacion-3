import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Ventana {

    private JFrame frame;
    private JLabel lblCronometro;
    private int segundos = 0;
    private Timer timer;
    JPanel panel = new JPanel();
    JButton btnPausar = new JButton("Pausar");
    JButton btnReanudar = new JButton("Reanudar");
    JButton btnReinicio = new JButton("Reinicio");
    int numeros[] = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16};
    JButton[] btnsCasillas = new JButton[numeros.length];

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    Ventana window = new Ventana();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the application.
     */
    public Ventana() {
        initialize();
        iniciarCronometro();
    }

    /**
     * Initialize the contents of the frame.
     */
    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 422, 439);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        Random r = new Random();

        panel.setBackground(Color.ORANGE);
        panel.setBounds(0, 0, 409, 355);
        frame.getContentPane().add(panel);
        panel.setLayout(null);

        lblCronometro = new JLabel("00:00:00");
        lblCronometro.setHorizontalAlignment(SwingConstants.CENTER);
        lblCronometro.setFont(new Font("Tahoma", Font.PLAIN, 19));
        lblCronometro.setBounds(152, 11, 105, 25);
        panel.add(lblCronometro);

        int x = 22;
        int y = 47;
        int index = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                String buttonText = (numeros[index] != 16) ? String.valueOf(numeros[index]) : "";
                JButton btn = new JButton(buttonText);
                btn.setFont(new Font("Tahoma", Font.BOLD, 20));
                btn.setBounds(x, y, 82, 63);
                panel.add(btn);
                btnsCasillas[index] = btn;
                x += 93;
                index++;
            }
            x = 22;
            y += 74;
        }

        for (JButton btn : btnsCasillas) {
                btn.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        movimientoBoton(btn);
                    }
                });
        }

        btnPausar.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnPausar.setBounds(0, 353, 128, 47);
        btnPausar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                pausa();
            }
        });
        frame.getContentPane().add(btnPausar);

        btnReanudar.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnReanudar.setBounds(128, 353, 153, 47);
        btnReanudar.setEnabled(false);
        btnReanudar.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reanudar();
            }
        });
        frame.getContentPane().add(btnReanudar);

        btnReinicio.setFont(new Font("Tahoma", Font.BOLD, 15));
        btnReinicio.setBounds(281, 353, 128, 47);
        btnReinicio.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                reinicio();
            }
        });
        frame.getContentPane().add(btnReinicio);
    }

    private void iniciarCronometro() {
        ActionListener taskPerformer = new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                segundos++;
                int horas = segundos / 3600;
                int minutos = (segundos % 3600) / 60;
                int segundosRestantes = segundos % 60;
                String tiempoFormateado = String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);
                lblCronometro.setText(tiempoFormateado);
            }
        };
        timer = new Timer(1000, taskPerformer);
        timer.start();
    }

    public void pausa() {
        timer.stop();
        for (JButton btn : btnsCasillas) {
            btn.setEnabled(false);
        }
        btnPausar.setEnabled(false);
        btnReanudar.setEnabled(true);
        btnReinicio.setEnabled(true);
    }

    public void reanudar() {
        timer.start();
        for (JButton btn : btnsCasillas) {
            btn.setEnabled(true);
        }
        btnPausar.setEnabled(true);
        btnReanudar.setEnabled(false);
    }

    public void reinicio() {
        segundos = 0;
        actualizarTiempo();
        
        Random r = new Random();
        for (int i = 0; i < numeros.length; i++) {
            int posAleatoria = r.nextInt(numeros.length);
            int temp = numeros[i];
            numeros[i] = numeros[posAleatoria];
            numeros[posAleatoria] = temp;
        }
        int index = 0;
        for (JButton btn : btnsCasillas) {
            if (btn != null) {
                String buttonText = (numeros[index] != 16) ? String.valueOf(numeros[index]) : ""; 
                btn.setText(buttonText);
                index++;
            }
        }
    }

    private void actualizarTiempo() {
        int horas = segundos / 3600;
        int minutos = (segundos % 3600) / 60;
        int segundosRestantes = segundos % 60;
        String tiempoFormateado = String.format("%02d:%02d:%02d", horas, minutos, segundosRestantes);
        lblCronometro.setText(tiempoFormateado);
    }

    private void movimientoBoton(JButton btn) {
        for (int i = 0; i < btnsCasillas.length; i++) {
            if (btnsCasillas[i] == btn) {
                if (i - 4 >= 0 && btnsCasillas[i - 4].getText().isEmpty()) {
                    intercambiarTextos(btnsCasillas[i], btnsCasillas[i - 4]);
                } else if (i + 4 < btnsCasillas.length && btnsCasillas[i + 4].getText().isEmpty()) {
                    intercambiarTextos(btnsCasillas[i], btnsCasillas[i + 4]);
                } else if (i % 4 != 0 && btnsCasillas[i - 1].getText().isEmpty()) {
                    intercambiarTextos(btnsCasillas[i], btnsCasillas[i - 1]);
                } else if ((i + 1) % 4 != 0 && btnsCasillas[i + 1].getText().isEmpty()) {
                    intercambiarTextos(btnsCasillas[i], btnsCasillas[i + 1]);
                }
                break;
            }
        }
    }

    private void intercambiarTextos(JButton btn1, JButton btn2) {
        String temp = btn1.getText();
        btn1.setText(btn2.getText());
        btn2.setText(temp);
    }
}
