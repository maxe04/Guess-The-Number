import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import javax.swing.UIManager.LookAndFeelInfo;
import javax.swing.UnsupportedLookAndFeelException;


public class Launcher {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
	
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
			        if ("Nimbus".equals(info.getName())) {
			            try {
							UIManager.setLookAndFeel(info.getClassName());
						} catch (ClassNotFoundException | InstantiationException | IllegalAccessException
								| UnsupportedLookAndFeelException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
			            break;
			        }
			    }
				
				Game game = new Game();
				game.show();
				
			}
			
		});
		
		
	
	}

}
