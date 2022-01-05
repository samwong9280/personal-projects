import static org.junit.Assert.*;
import org.junit.Test;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;

/*Pleas Not that you must run each test individually since we are doing system testing*/
public class MainClassTest {
    /**
     * Tests whether IR Election can handle multiple csv files.
     */
    @Test
    public void testMultipleCSVIR() {
        String parentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        String fileName = parentPath + "/testing/IRTest100.csv";
        String fileName1 = parentPath + "/testing/IRTest2.csv";
        BufferedReader br = null;
        Main main1 = new Main();
        String[] args = {fileName, fileName1};
        main1.mainCaller(args);
        IR_Election obj = (IR_Election) main1.getObj();
        assertEquals("Rosen(D)", obj.getWinner1());
    }

    /**
     * Tests whether main can handle multiple csv files and run OPL election
     * Tests whether the number of ballots created from two files is correct.
     *
     */
    @Test
    public void testMultipleCSVOPL() {
        String parentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        String fileName = parentPath + "/testing/OPLTest3.csv";
        String fileName1 = parentPath + "/testing/OPLTest5.csv";
        BufferedReader br = null;
        Main main1 = new Main();
        String[] args = {fileName, fileName1};
        main1.mainCaller(args);
        OPL_Election obj = (OPL_Election) main1.getObj();
        assertEquals(16, obj.getTotalVote());
    }

    /**
     * Tests whether main can handle multiple csv files and run PO election
     * Tests whether the number of ballots created from two files is correct.
     *
     */

    @Test
    public void testMultipleCSVPO() {
        String parentPath = Paths.get(".").toAbsolutePath().normalize().toString();
        String fileName = parentPath + "/testing/POTest1.csv";
        String fileName1 = parentPath + "/testing/POTest2a.csv";
        BufferedReader br = null;
        Main main1 = new Main();
        String[] args = {fileName, fileName1};
        main1.mainCaller(args);
        PO_Election obj = (PO_Election) main1.getObj();
        assertEquals(18, obj.getTotalVote());
    }


}
