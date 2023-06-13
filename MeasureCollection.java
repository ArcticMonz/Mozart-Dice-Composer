import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class MeasureCollection {
    private ArrayList<String> measures;

    public MeasureCollection(){
        measures = new ArrayList<String>();
        extractMeasures(measures);
    }

    private void extractMeasures(ArrayList<String> measures){
        try {
            File mImp = new File("C:\\Users\\Francesca\\Desktop\\measuresImplementation.txt");
            Scanner reader = new Scanner(mImp);
            while (reader.hasNextLine())
                measures.add(reader.nextLine());
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }

    public void printMeasures(){
        for(String m: measures)
            System.out.println(m);
    }

    public String produceStaccato(int[] notes){
        String res = "";
        for(int i=0; i< notes.length; i++) {
            res += measures.get(notes[i] - 1);
            res += " | ";
        }
        return res;
    }

}
