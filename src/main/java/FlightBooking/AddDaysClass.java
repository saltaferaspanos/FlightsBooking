package FlightBooking;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;


public class AddDaysClass {
	
	private final static DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public Date AddWorkingDays(Calendar CalDate, int Days) {
		
		Date targetDate=null;
		CalDate.add(Calendar.DAY_OF_YEAR, Days-1);
	    System.out.println(" Target Calendar Date: "+ CalDate.getTime());
	    
		do {
			CalDate.add(Calendar.DAY_OF_YEAR, 1);
			targetDate=CalDate.getTime();
			System.out.println("Is Working Day?: "+isWorkingDay(CalDate)+" || Date: "+ dateFormat.format(targetDate));
		}while(!isWorkingDay(CalDate));
		
		return targetDate;
	}
	
	public Date AddDays(Calendar CalDate, int Days) {
		
		System.out.println("calendar date: " + CalDate.getTime());
		CalDate.add(Calendar.DAY_OF_YEAR, Days);
		Date TargetDate= CalDate.getTime();
		System.out.println("New Date is :" + dateFormat.format(TargetDate));
		
		return TargetDate;	
	}
	
	 private static boolean isWorkingDay(Calendar cal) {
	        int dayOfWeek = cal.get(Calendar.DAY_OF_WEEK);
	        int month = cal.get(Calendar.MONTH)+1;//because first value of month is 0.
	        int day = cal.get(Calendar.DAY_OF_MONTH);//first value of day is 1.
	        int year = cal.get(Calendar.YEAR);
	        
	        Calendar Eastern = EasternCalc(year);
	        Calendar MondayEastern, CleanMonday, HolySpirit;
	        MondayEastern= EasternCalc(year);
	        MondayEastern.add(Calendar.DAY_OF_YEAR, 1);
	        
	        CleanMonday = EasternCalc(year);
	        CleanMonday.add(Calendar.DAY_OF_YEAR, -48);
	        
	        HolySpirit = EasternCalc(year);
	        HolySpirit.add(Calendar.DAY_OF_YEAR,50);
	        
//	        System.out.println("Eastern Date is "+Eastern.getTime());
//	        System.out.println("MondayEastern Date is "+MondayEastern.getTime());
//	        System.out.println("CleanMonday Date is "+CleanMonday.getTime());
//	        System.out.println("HolySpirit Date is "+HolySpirit.getTime());
	        
	        if (dayOfWeek == Calendar.SUNDAY || dayOfWeek == Calendar.SATURDAY)
	            return false;
	        if (month == 1 && day==1)
	        	return false;
	        if (month == 1 && day==6)
	        	return false;
	        if (month == 3 && day==2)
	        	return false;
	        if (month == 3 && day==25)
	        	return false;
	        if (month == 5 && day==1)
	        	return false;
	        if (month == 8 && day==15)
	        	return false;
	        if (month == 10 && day==28)
	        	return false;
	        if (month == 12 && day==25)
	        	return false;
	        if (month == 12 && day==26)
	        	return false;
	        //Sunday of Eastern
	        if (dateFormat.format(cal.getTime()).equals(dateFormat.format(Eastern.getTime())))
	        	return false;
	        //Monday of Eastern
	        if (dateFormat.format(cal.getTime()).equals(dateFormat.format(MondayEastern.getTime())))
	        	return false;
	        //Clean Monday feast
	        if (dateFormat.format(cal.getTime()).equals(dateFormat.format(CleanMonday.getTime())))
	        	return false;
	        //Holy Spirit feast
	        if (dateFormat.format(cal.getTime()).equals(dateFormat.format(HolySpirit.getTime())))
	        	return false;
	        	// tests for other holidays here
	        
	        return true;
	 }

	 
	private static Calendar EasternCalc(int year) {
		 
		 Calendar myCal = Calendar.getInstance();
		 int Calc=0;	     
	     int theDay=0;
	     int theMonth=2; //March	
	     int theYear=year;
	     int myDayOfWeek=0;
//	     theYear=2021; //test function
//	     System.out.println("The Year is: "+theYear);
	     
	     Calc = (44-((((theYear-2)%19)*11)%30))+13;
//	     System.out.println("Calculation: "+Calc);
	     
	     if (Calc/30>0) {
	        theMonth++;
	        theDay=Calc-31; // March has 31 days
//	        System.out.println("The day is "+ theDay + " and the month is: "+theMonth);
	    }
	    else if(Calc/30>1) {
	    	theMonth++;
	        theDay=Calc-30; // April has 31 days
//	        System.out.println("The day is "+ theDay + " and the month is: "+theMonth);
	    	
	    }
	    else {
	    	theDay=Calc;
//	    	System.out.println("The day is "+ theDay + " and the month is: "+theMonth);
	    }
	    
	     
	     myCal.set(Calendar.YEAR, theYear);
	     myCal.set(Calendar.MONTH, theMonth);
	     myCal.set(Calendar.DAY_OF_MONTH, theDay);
	     
	    // find next Sunday 
	    do {
	       	myCal.add(Calendar.DATE, 1);
	       	myDayOfWeek=myCal.get(Calendar.DAY_OF_WEEK);
	    }while (myDayOfWeek != Calendar.SUNDAY);
	        
	    return myCal;   
	 }

}



