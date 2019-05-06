/*
 * Emma Prager
 * 03/30/2019
 * Records.java
 * Lab 03
 */

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.Arrays;
import java.util.Date;

public class Records extends BankRecords {

//create formatted object to write output directly to console & file
	static FileWriter fw = null;
	static PrintWriter pw = null;

	public Records() {
		try {
			fw = new FileWriter("bankrecords.txt");
			pw = new PrintWriter(fw);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		Records br = new Records();
		br.readData();
		
		System.out.printf("Data analytic results:\n\n");
		try {
			fw.write("Data analytic results:\n\n");
		} catch (IOException e2) {
			// TODO Auto-generated catch block
			e2.printStackTrace();
		}

		// call functions to perform analytics
		avgComp();     // analyze average income per loc
		femsComp();  // female count w. mortgage & savings accounts 
		malesComp(); // male counts with both car & 1 child per location
		
		Date date= new Date();	
		String timeStamp = DateFormat.getDateTimeInstance().format(date);
		
		try {
			fw.write("\nEmma Prager\n");
			fw.write(timeStamp); //put date and time here
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		// *** close out file object ***//

		try {
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	  private static void avgComp() {

		Arrays.sort(robjs, new SexComparator());

		// set up needed variables to gather counts & income by sex 
		// to determine average income by sex
          
		int maleCt = 0, femCt = 0;
        double maleInc =0, femInc = 0;
        
		for (int i = 0; i < robjs.length; i++)
			if (robjs[i].getSex().equals("FEMALE")) {
				++femCt;
				femInc += robjs[i].getIncome();
			}  
			else {
				++maleCt;
				maleInc += robjs[i].getIncome();
			}
			 
		// display resulting averages to console and to file
		 
		System.out.printf("Average income Females: $%.2f\n", (femInc/femCt));
		System.out.printf("Average income Males: $%.2f\n\n", (maleInc/maleCt));
		
		pw.printf("Average income Females: $%.2f\n", (femInc/femCt));
		pw.printf("Average income Males: $%.2f\n\n", (maleInc/maleCt));
	}

	  
	  private static void femsComp() {
		  	Arrays.sort(robjs, new SexComparator());

			// set up needed variables to gather counts & income by sex 
			// to determine average income by sex
	          
			int count = 0;
	        
			for (int i = 0; i < robjs.length; i++)
				if (robjs[i].getSex().equals("FEMALE") && robjs[i].getMortgage().equals("YES") && robjs[i].getSave_act().equals("YES")) {
					count++;
				} 
			
			// display resulting averages to console and to file
		  System.out.printf("Number of Females with mortgage & savings acct: %d\n\n", count);
		  pw.printf("Number of Females with mortgage & savings acct: %d\n\n", count);
	  }
	  
	  private static void malesComp() {
		  Arrays.sort(robjs, new LocationComparator());

			// set up needed variables to gather counts & income by sex 
			// to determine average income by sex
	          
			int city = 0, rural = 0, sub = 0, town = 0;
	        
			for (int i = 0; i < robjs.length; i++)
				if (robjs[i].getSex().equals("MALE") && robjs[i].getChildren()==1 && robjs[i].getCar().equals("YES")) {
					if(robjs[i].getRegion().equals("INNER_CITY")) {
						++city;
					}
					else if(robjs[i].getRegion().equals("RURAL")) {
						++rural;
					}
					else if(robjs[i].getRegion().equals("SUBURBAN")) {
						++sub;
					}
					else if(robjs[i].getRegion().equals("TOWN")) {
						++town;
					}
				}  
		
			// display resulting averages to console and to file 
		  System.out.printf("Inner-city region Males with car & 1 child: %d\n", city);
		  System.out.printf("Rural region Males with car & 1 child: %d\n", rural);
		  System.out.printf("Suburban region Males with car & 1 child: %d\n", sub);
		  System.out.printf("Town region Males with car & 1 child: %d\n", town);
		  
		  pw.printf("Inner-city region Males with car & 1 child: %d\n", city);
		  pw.printf("Rural region Males with car & 1 child: %d\n", rural);
		  pw.printf("Suburban region Males with car & 1 child: %d\n", sub);
		  pw.printf("Town region Males with car & 1 child: %d\n", town);
	  }

}
