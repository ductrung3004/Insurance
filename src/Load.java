

public class Load {
    public static void main(String[] args) {
        Loading Load =new Loading();
        Load.setVisible(true);
        try{

            for(int i=0;i<=100;i++) {
                Thread.sleep(20);
                Load.load.setText(Integer.toString(i)+"%");
                if(i==100){
                    Load.setVisible(false);
                    Main.main(new String[0]);
                }
            }
        }
        catch(InterruptedException e){
        }
    }

}

