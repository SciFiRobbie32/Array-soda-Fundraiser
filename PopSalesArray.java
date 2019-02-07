import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class PopSalesArray {
static String pLname, pFname, pAddress, pCity, pState, pTeam, More = "T", ValSw = "ON", oPopType, oTeam, record;
static String[] aPopType = {"Coke", "Diet Coke", "Mello Yello", "Cherry Coke", "Diet Cherry Coke", "Sprite"};
static int pCases = 0, pZip = 0, pZip2 = 0, pPopType = 0, cErrCount = 0, cTeam, cPctr = 0, cErrPctr = 0, cErrLine = 0, cLine = 0, cDiposArrayNum;
static Scanner myScanner;
static PrintWriter ErrorPW;
static PrintWriter RepPW;
static String[] aTeam = {"A", "B", "C", "D","E"};
static String[] oErrMsg = {"Invalid. Last Name Empty.", "Invalid. First Name Empty.", "Invalid. Address incorrect.", "Invalid. State Must be IA, IL, MI, MO, NE, OR WI.", "Invalid. Pop Type needs to be 1,2,3,4,5, or 6.", 
		"Invalid. Team code should be A,B,C,D, or E.", "Invalid. Zip Code.", "Invalid. Zip Code Error, not numeric.", "Invalid. Pop Type To small.", "Invalid. Pop Type Error, not numeric.",
		"Invalid. Cases Error, not numeric.", "Invalid. Adress empty.", "Invalid. Cases Error, Cases to large.", "Invalid. Cases Error, Cases to small or nonnumeric.", "Invalid. City is empty."};
static double[] aTeamFund = new double[5];
static double[] aDipos = {0.05, 0.1, 0.0};
static int[] aCases = new int[6];
static double cTotal, cDipos;
static LocalDate today = LocalDate.now();
static DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");

	public static void main(String[] args) {
		Init();
		while (More.equals("T")) {
			// Validation check
			if (ValSw.equals("ON")) {
				Val();
			}
			//perform calcs and output only if Validation was turned off meaning that the record is good
			if(ValSw.equals("OFF")) {
				Calcs();
				Output();
			}
			Input();
		}
		System.out.print("Program ended, have a good day.");
		Totals();
	}
	public static void Val() {
		//turn validation switch on in case the next record is bad.
		ValSw = "ON";
		// check last name for spaces
		if (pLname.trim().isEmpty()) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[0]);
			ErrorCount();
			return;
		}
		//check first name for empty value
		if (pFname.trim().isEmpty()) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[1]);
			ErrorCount();
			return;
		}
		//check address for empty value
		if (pAddress.trim().isEmpty()) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[2]);
			ErrorCount();
			return;
		}
		if (pState.trim().isEmpty()) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[3]);
			ErrorCount();
			return;
		}
		//make sure the zip isn't zero (see the catch for why it would be zero)
		if (pZip == 0) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[6]);
			ErrorCount();
			return;
		}
		if (pZip2 == 0) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[6]);
			ErrorCount();
			return;
		}
		//make sure the pop type isnt zero (see catch for why it would be zero)
		if (pPopType == 0) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[8]);
			ErrorCount();
			return;
		}
		//check for empty city
		if (pCity.trim().isEmpty()) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[14]);
			ErrorCount();
			return;
		}
		//check the state default for errors.
		switch (pState) {
		case "IA":
			break;
		case "IL":
			break;
		case "MI":
			break;
		case "MO":
			break;
		case "NE":
			break;
		case "WI":
			break;
		default:
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[3]);
			ErrorCount();
			return;
		}
		//same error check as for the state but with the pop type.
		switch (pPopType) {
		case 1:
			break;
		case 2:
			break;
		case 3:
			break;
		case 4:
			break;
		case 5:
			break;
		case 6:
			break;
		default:
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[4]);
			ErrorCount();
			return;
		}
		//same as before
		switch (pTeam) {
		case "A":
			break;
		case "B":
			break;
		case "C":
			break;
		case "D":
			break;
		case "E":
			break;
		default:
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[5]);
			ErrorCount();
			return;
		}
		//checking for inccorect input and or catch
		if (pCases < 1) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[13]);
			ErrorCount();
			return;
		}
		ValSw = "OFF";
	}
	public static void ErrorCount() {
		//error count and heading caller for fifty line hit
		cErrCount++;
		cErrLine++;
		if (cErrLine == 50) {
			ErrHeadings();
			cErrLine = 0;
		}
	}
	public static void Init(){
		//scanner set up and file set up
		myScanner = new Scanner(System.in);
		try {
			myScanner = new Scanner(new File("CBLPOPSL.DAT")); //input file
			myScanner.useDelimiter(System.getProperty("line.separator"));
			}
		
			catch (FileNotFoundException e) {
				System.out.print("No File Found");
				System.exit(1);	
			}
		
		try {
			RepPW = new PrintWriter(new File ("JAVAPOPSLB.PRT")); //report file
			}
			catch (Exception e) {
			}
		try {
			ErrorPW = new PrintWriter(new File ("JAVAPOPERB.PRT")); //errorfile
			}
			catch (Exception e) {
			}
		//set up for report file and error file as well as record 1 for validation
		Headings();
		ErrHeadings();
		Input();
		
	}
	public static void ErrHeadings() {
		cErrPctr+=1;
		ErrorPW.format("%6s%10s%36s%28s%44s%5s%2s%n", "Date: ", today.format(dtf), " ", "Albia Soccer CLub Fundraiser", " ", "Page:", cErrPctr);
		ErrorPW.format("%56s%10s%1s%8s%n", " ", "Robbie's", " ", "Division");
		ErrorPW.format("%60s%12s%n%n", " ", "Error Report");
	}
	public static void Headings() {
		cPctr+=1;
		RepPW.format("%6s%10s%36s%28s%44s%5s%2s%n", "Date: ", today.format(dtf), " ", "Albia Soccer CLub Fundraiser", " ", "Page:", cPctr);
		RepPW.format("%56s%10s%1s%8s%n", " ", "Robbie's", " ", "Division");
		RepPW.format("%60s%12s%n", " ", "Sales Report");
		RepPW.format("%3s%9s%8s%10s%7s%4s%8s%5s%1s%8s%3s%8s%13s%8s%6s%11s%6s%11s%n", " ", "Last Name", " ", "First Name", " ", "City", " ", "State", " ", "Zip Code", " ", "PopType", " ", "Quantity", " ", "Deposit AMT", " ", "Total Sales" );
	}
	public static void Input(){
		//scanner set to gather data from the file, set up for convenience of the dat file
		if (myScanner.hasNext()) {
			record = myScanner.next();
			pLname = record.substring(0,14); //1-15
			pFname = record.substring(14,29); //15-30
			pAddress = record.substring(29,44); //30-45
			pCity = record.substring(44,54); //45-55
			pState = record.substring(54, 56); //55-57
			try {
				String iZip = record.substring(56, 61); //57-62
				pZip = Integer.parseInt(iZip);
				iZip = record.substring(61,65); //62-66
				pZip2 = Integer.parseInt(iZip);
			}
			catch (Exception e) {
				//in case of an error the zips are set to 0 for the validation.
				pZip = 0;
				pZip2 = 0;
			}
			try {
				String iPopType = record.substring(65,67); //66-68
				pPopType = Integer.parseInt(iPopType);
			}
			catch (Exception e) {
				// in case of an error the pop type is set to an invalid number (note: this doesn't effect the array)
				pPopType = 8;
			}
			try {
			String iCases = record.substring(67,69);
			pCases = Integer.parseInt(iCases);
			}
			catch (Exception e) {
				//same as the other catches
				pCases = 0;
			}
			try {
			pTeam = record.substring(69,70);
			}
			catch (Exception e) {
				//same as the other catches set to inproper variable in case the error is due to null or our of range to not blow up the program.
				pTeam = "G";
			}
			
		}
		else {
			//stop the program after the file is empty.
			More = "F";
		}
	}
	public static void Calcs(){
		// case structure to select what number of a set array.
		switch (pState) {
		case "IA":
			cDiposArrayNum = 0;
			break;
		case "IL":
			cDiposArrayNum = 2;
			break;
		case "MI":
			cDiposArrayNum = 1;
			break;
		case "MO":
			cDiposArrayNum = 2;
			break;
		case "NE":
			cDiposArrayNum = 0;
			break;
		case "WI":
			cDiposArrayNum = 0;
			break;
		}
		// case structure to select what number of a set array.
		switch (pTeam) {
		case "A":
			cTeam = 0;
			break;
		case "B":
			cTeam = 1;
			break;
		case "C":
			cTeam = 2;
			break;
		case "D":
			cTeam = 3;
			break;
		case "E":
			cTeam = 4;
			break;
		}
		//in case there were errors that made it passed the validation, as it didn't seem to work in the validation.
		if (pPopType > 6) {
			ErrorPW.format("%n%-71s", record);
			ErrorPW.format("%60s%n", oErrMsg[4]);
			return;
		}
		//change the pop type number to fit the array
		pPopType--;
		aCases[pPopType] += pCases;
		oPopType = aPopType[pPopType];
		cDipos = (24*pCases)*aDipos[cDiposArrayNum];
		cTotal += 18.71*pCases+cDipos;
		aTeamFund[cTeam] += cTotal;
	}
	public static void Output(){
		DecimalFormat dft = new DecimalFormat("$##,###.00");
		String oTotal, oDipos;
		// frmat the cash valuse
		oTotal = dft.format(cTotal);
		oDipos = dft.format(cDipos);
		//print them out and then add to line counter
		RepPW.format("%n%3s%15s%2s%15s%2s%10s%3s%5s%1s%4s%2s%16s%11s%2s%11s%-9s%8s%-9s%n", " ", pLname, " ", pFname, " ", pCity, " ", pZip, "-", pZip2, " ", oPopType, " ", pCases, " ", oDipos, " ", oTotal);
		cLine++;
		//check line counter
		if (cLine == 50) {
			RepPW.format("%n");
			Headings();
			cLine = 0;
		}
		//zero everything and set validation switch to on to check for the next record.
		cTotal = 0;
		cDipos = 0;
		ValSw = "ON";
		
	}
	public static void Totals() {
		DecimalFormat dft = new DecimalFormat("$##,###.00");
		RepPW.format("%n");
		Headings();
		RepPW.format("%n");
		RepPW.format("%-14s%n%n", "Grand Totals: ");
		// loop 1 for soda types
		for (int i = 0; i<3; i++) {
			oPopType = aPopType[i];
			RepPW.format("%3s%-5s%1s%7s%6s", " ", oPopType, " ", aCases[i], " ");
		}
		RepPW.format("%n%n");
		// loop 2 for soda types
		for (int i = 3; i<6; i++) {
			oPopType = aPopType[i];
			RepPW.format("%3s%-5s%1s%7s%6s", " ", oPopType, " ", aCases[i], " ");
		}
		RepPW.format("%n%n%-13s%n", "Team Totals: ");
		// loop for team totals
		for (int c = 0; c<5; c++) {
			String oTeamFund;
			oTeam = aTeam[c];
			oTeamFund = dft.format(aTeamFund[c]);
			RepPW.format("%n%3s%1s%1s%6s%n", " ", oTeam, " ", oTeamFund);
		}
		//finalization of error report and then closing files.
		String oTotErr;
		oTotErr = Integer.toString(cErrCount);
		ErrorPW.format("%n%13s%5s", "Total Errors: ", oTotErr);
		RepPW.close();
		ErrorPW.close();
	}
}
