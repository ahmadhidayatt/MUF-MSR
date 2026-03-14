package KomiMsCredit;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;
// --- <<IS-END-IMPORTS>> ---

public final class utils

{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void calculateAngsBunga (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateAngsBunga)>> ---
		// @sigtype java 3.5
		// [i] object:0:required pokok_hutang
		// [i] object:0:required flate_rate
		// [i] object:0:required tenor
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	initPokok_hutang = IDataUtil.getString( pipelineCursor, "pokok_hutang" );
		String	initFlate_rate = IDataUtil.getString( pipelineCursor, "flate_rate" );
		String	initTenor = IDataUtil.getString( pipelineCursor, "tenor" );
		pipelineCursor.destroy();
		
		Double pokok_hutang = parseToDouble(initPokok_hutang);
		Double flate_rate = parseToDouble(initFlate_rate);
		Integer tenor = parseToInteger(initTenor);
		
		double calculateResult = Math.round(pokok_hutang * (flate_rate / 100) * (tenor / 12) / tenor);
		Double result = calculateResult;
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void calculateAngsuranArr (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateAngsuranArr)>> ---
		// @sigtype java 3.5
		// [i] object:0:required pokok_hutang
		// [i] object:0:required tempRate
		// [i] object:0:required tenor
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	init_pokok_hutang = IDataUtil.getString( pipelineCursor, "pokok_hutang" );
		String	init_tempRate = IDataUtil.getString( pipelineCursor, "tempRate" );
		String	init_tenor = IDataUtil.getString( pipelineCursor, "tenor" );
		pipelineCursor.destroy();
		
		Double pokok_hutang = parseToDouble(init_pokok_hutang);
		Double tempRate = parseToDouble(init_tempRate);
		Integer tenor = parseToInteger(init_tenor);
		
		Double result = roundUpRibuan((pokok_hutang * (1 + (tempRate / 1200))) / tenor);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void calculateAngsuranCommon (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateAngsuranCommon)>> ---
		// @sigtype java 3.5
		// [i] object:0:required eff_rate
		// [i] object:0:required tenor
		// [i] object:0:required pokok_hutang
		// [i] object:0:required fv
		// [i] object:0:required type
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	init_eff_rate = IDataUtil.getString( pipelineCursor, "eff_rate" );
		String	init_tenor = IDataUtil.getString( pipelineCursor, "tenor" );
		String	init_pokok_hutang = IDataUtil.getString( pipelineCursor, "pokok_hutang" );
		
		String	init_fv = IDataUtil.getString( pipelineCursor, "fv" );
		String	init_type = IDataUtil.getString( pipelineCursor, "type" );
		pipelineCursor.destroy();
		
		Double eff_rate = parseToDouble(init_eff_rate);
		Integer tenor = parseToInteger(init_tenor);
		Double pokok_hutang = parseToDouble(init_pokok_hutang);
		Double fv = parseToDouble(init_fv);
		Integer type = parseToInteger(init_type);
		
		Double result = roundUpRibuan(financePmt((eff_rate / 100) / 12, tenor, -pokok_hutang, fv, type));
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void calculateFlatRateSearch (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateFlatRateSearch)>> ---
		// @sigtype java 3.5
		// [i] object:0:required tenorDouble
		// [i] object:0:required eff_rate
		// [i] field:0:required tipe
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	initTenorDouble = IDataUtil.getString( pipelineCursor, "tenorDouble" );
			String	initEff_rate = IDataUtil.getString( pipelineCursor, "eff_rate" );
			String	tipe = IDataUtil.getString( pipelineCursor, "tipe" );
		pipelineCursor.destroy();
		
		Double tenorDouble = parseToDouble(initTenorDouble);
		Double eff_rate  = parseToDouble(initEff_rate);
		
		Double result = tenorDouble + eff_rate;
		
		if (tipe.equalsIgnoreCase("ADV")) {
			result = ((tenorDouble * (eff_rate / 1200.0) / ((1 + (eff_rate / 1200.0) - (Math.pow((1 + (eff_rate / 1200.0)), (-tenorDouble + 1)))))) - 1) * (1200.0 / tenorDouble);
		} else if (tipe.equalsIgnoreCase("ARR")) {
			result = ((tenorDouble * (eff_rate / 1200.0) / (1 - Math.pow(((1 + eff_rate / 1200.0)), (-tenorDouble))) - 1) * (1200.0 / tenorDouble));
		}
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void calculateFlatRateSearchV2 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateFlatRateSearchV2)>> ---
		// @sigtype java 3.5
		// [i] object:0:required eff_rate
		// [i] object:0:required tenor
		// [i] field:0:required tipe
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	init_eff_rate = IDataUtil.getString( pipelineCursor, "eff_rate" );
		String	init_tenor = IDataUtil.getString( pipelineCursor, "tenor" );
		String	tipe = IDataUtil.getString( pipelineCursor, "tipe" );
		pipelineCursor.destroy();
		
		Double eff_rate = parseToDouble(init_eff_rate);
		Integer tenor = parseToInteger(init_tenor);
		double flat_rate_search = 0.0;
		
		if (tipe.equals("ADV")) {
			flat_rate_search = ((tenor * (eff_rate / 1200.0) / ((1 + (eff_rate / 1200.0) - (Math.pow((1 + (eff_rate / 1200.0)), (-tenor + 1)))))) - 1) * (1200.0 / tenor);
		} else if (tipe.equals("ARR")) {
			flat_rate_search = ((tenor * (eff_rate / 1200.0) / (1 - Math.pow(((1 + eff_rate / 1200.0)), (-tenor))) - 1) * (1200.0 / tenor));
		}
		
		Double result = flat_rate_search;
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void calculateRate (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateRate)>> ---
		// @sigtype java 3.5
		// [i] object:0:required nper
		// [i] object:0:required pmt
		// [i] object:0:required pv
		// [i] object:0:required fv
		// [i] object:0:required type
		// [i] object:0:required guess
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	initNper = IDataUtil.getString( pipelineCursor, "nper" );
		String	initPmt = IDataUtil.getString( pipelineCursor, "pmt" );
		String	initPv = IDataUtil.getString( pipelineCursor, "pv" );
		String	initFv = IDataUtil.getString( pipelineCursor, "fv" );
		String	initType = IDataUtil.getString( pipelineCursor, "type" );
		String	initGuess = IDataUtil.getString( pipelineCursor, "guess" );
		pipelineCursor.destroy();
		
		Double nper = parseToDouble(initNper);
		Double pmt = parseToDouble(initPmt);
		Double pv = parseToDouble(initPv);
		Double fv = parseToDouble(initFv);
		Double type = parseToDouble(initType);
		Double guess = parseToDouble(initGuess);
		
		int FINANCIAL_MAX_ITERATIONS = 200;// Bet accuracy with 128
		double FINANCIAL_PRECISION = 0.0000001;// 1.0e-8
		
		double y, y0, y1, x0, x1 = 0, f = 0, i = 0;
		double rate = guess;
		if (Math.abs(rate) < FINANCIAL_PRECISION) {
			y = pv * (1 + nper * rate) + pmt * (1 + rate * type) * nper + fv;
		} else {
			f = Math.exp(nper * Math.log(1 + rate));
			y = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
		}
		y0 = pv + pmt * nper + fv;
		y1 = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
		
		// find root by Newton secant method
		i = x0 = 0.0;
		x1 = rate;
		while ((Math.abs(y0 - y1) > FINANCIAL_PRECISION) && (i < FINANCIAL_MAX_ITERATIONS)) {
			rate = (y1 * x0 - y0 * x1) / (y1 - y0);
			x0 = x1;
			x1 = rate;
		
			if (Math.abs(rate) < FINANCIAL_PRECISION) {
				y = pv * (1 + nper * rate) + pmt * (1 + rate * type) * nper + fv;
			} else {
				f = Math.exp(nper * Math.log(1 + rate));
				y = pv * f + pmt * (1 / rate + type) * (f - 1) + fv;
			}
		
			y0 = y1;
			y1 = y;
			++i;
		}
		
		Double result = rate;
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void calculateRateScala (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateRateScala)>> ---
		// @sigtype java 3.5
		// [i] object:0:required nper
		// [i] object:0:required pmt
		// [i] object:0:required pv
		// [i] object:0:required type
		// [o] object:0:required calculateResult
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	nperInput = IDataUtil.getString( pipelineCursor, "nper" );
		String	pmtInput = IDataUtil.getString( pipelineCursor, "pmt" );
		String	pvInput = IDataUtil.getString( pipelineCursor, "pv" );
		String	typeInput = IDataUtil.getString( pipelineCursor, "type" );
		pipelineCursor.destroy();
		
		Double nper = parseToDouble(nperInput);
		Double pmt = parseToDouble(pmtInput);
		Double pv = parseToDouble(pvInput);
		Integer type = parseToInteger(typeInput);
		
		double eror = 0.000000000001;
		double high = 1.00;
		double low = 0.00;
		double calc = 0.0;
		double stop = 1;
		double tenor = 12;
		double angs = roundAt(pmt, 5);
		
		double loop = 0;
		double rate = 0.1;
		
		while (stop == 1) {
			calc = 1.0 / ((1 - (1.0 / Math.pow((1 + rate), (nper - type)))) / rate + type);
			calc -= angs / pv;
			if (loop == 100000) {
				stop = 0;
				rate = 0;
			} else if (calc > eror) {
				high = rate;
				rate = (high + low) / 2;
			} else if (calc < -eror) {
				low = rate;
				rate = (high + low) / 2;
			} else {
				stop = 0;
			}
			loop += 1;
		}
		
		Double calcResult = roundAt(rate * tenor * 100.0, 5);
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "calculateResult", calcResult );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void calculateTotalAngsBunga (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateTotalAngsBunga)>> ---
		// @sigtype java 3.5
		// [i] object:0:required pokok_hutang
		// [i] object:0:required angs_total
		// [i] object:0:required flat_rate
		// [i] object:0:required tenor
		// [i] field:0:required tipe
		// [i] field:0:required obj_group_code
		// [o] object:0:required resultTotalAngsBunga
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	init_pokok_hutang = IDataUtil.getString( pipelineCursor, "pokok_hutang" );
		String	init_angs_total = IDataUtil.getString( pipelineCursor, "angs_total" );
		String	init_flat_rate = IDataUtil.getString( pipelineCursor, "flat_rate" );
		String	init_tenor = IDataUtil.getString( pipelineCursor, "tenor" );
		String	tipe = IDataUtil.getString( pipelineCursor, "tipe" );
		String	obj_group_code = IDataUtil.getString( pipelineCursor, "obj_group_code" );
		pipelineCursor.destroy();
		
		Double pokok_hutang = parseToDouble(init_pokok_hutang);
		Double angs_total = parseToDouble(init_angs_total);
		Double flat_rate = parseToDouble(init_flat_rate);
		Integer tenor = parseToInteger(init_tenor);
		double total_angs_bunga = 0.0;
		
		
		if (tipe.equalsIgnoreCase("ADV")){
			if (obj_group_code.equals("002")) {
				//mobil
				total_angs_bunga = Math.round((pokok_hutang * (round7(flat_rate) / 1200.0)) * tenor);
			} else {
				//motor
				total_angs_bunga = Math.round(((pokok_hutang + angs_total) * (round7(flat_rate) / 1200.0)) * tenor);
			}
		} else if (tipe.equalsIgnoreCase("ARR")) {
			total_angs_bunga = Math.round(((pokok_hutang) * (round7(flat_rate) / 1200.0)) * tenor);
		}
		
		Double resultTotalAngsBunga = total_angs_bunga;
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "resultTotalAngsBunga", resultTotalAngsBunga );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void calculateTotalAngsBungaV2 (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(calculateTotalAngsBungaV2)>> ---
		// @sigtype java 3.5
		// [i] object:0:required pokok_hutang
		// [i] object:0:required flat_rate
		// [i] object:0:required tenor
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	init_pokok_hutang = IDataUtil.getString( pipelineCursor, "pokok_hutang" );
		String	init_flat_rate = IDataUtil.getString( pipelineCursor, "flat_rate" );
		String	init_tenor = IDataUtil.getString( pipelineCursor, "tenor" );
		pipelineCursor.destroy();
		
		Double pokok_hutang = parseToDouble(init_pokok_hutang);
		Double flat_rate = parseToDouble(init_flat_rate);
		Integer tenor = parseToInteger(init_tenor);
		
		double calcResult = Math.round((pokok_hutang * (round7(flat_rate) / 1200.0)) * tenor);
		Double result = calcResult;
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		//Object result = new Object();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void castDouble (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(castDouble)>> ---
		// @sigtype java 3.5
		// [i] object:0:required objInput
		// [o] object:0:required doubleResult
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	objInput = IDataUtil.getString( pipelineCursor, "objInput" );
		pipelineCursor.destroy();
		
		double result = parseToDouble(objInput);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		Object doubleResult = new Object();
		IDataUtil.put( pipelineCursor_1, "doubleResult", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void convertDoubleToBigDecimal (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convertDoubleToBigDecimal)>> ---
		// @sigtype java 3.5
		// [i] object:0:required temp_eff_rate
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	init_temp_eff_rate = IDataUtil.getString( pipelineCursor, "temp_eff_rate" );
		pipelineCursor.destroy();
		
		Double temp_eff_rate = parseToDouble(init_temp_eff_rate);
		double result = new BigDecimal(Double.toString(temp_eff_rate), MathContext.UNLIMITED)
		        .setScale(5, RoundingMode.HALF_UP)
		        .doubleValue();
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		//Object result = new Object();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void convertMinusNumber (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(convertMinusNumber)>> ---
		// @sigtype java 3.5
		// [i] object:0:required inputNumber
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	inputNumber = IDataUtil.getString( pipelineCursor, "inputNumber" );
		pipelineCursor.destroy();
		
		Double number = parseToDouble(inputNumber);
		Double result = -number;
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void floatToString (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(floatToString)>> ---
		// @sigtype java 3.5
		// [i] object:0:required inputNumber
		// [i] field:0:required pattern
		// [o] field:0:required formattedString
 IDataCursor cursor = pipeline.getCursor();
    
 Long inputNumber = null;
 String pattern = null;
 
 String formattedString = null;

 if (cursor.first("inputNumber")) {
     Object value = cursor.getValue();
     if (value instanceof Long) {
         inputNumber = (Long) value;
     } else if (value instanceof String) {
         inputNumber = Long.parseLong((String) value);
     }
 }

 if (cursor.first("pattern")) {
     pattern = (String) cursor.getValue();
 }
    
    if (inputNumber != null && pattern != null) {
        try {
            double number = inputNumber.doubleValue();
            DecimalFormat df = new DecimalFormat(pattern);
            formattedString = df.format(number);
        } catch (NumberFormatException e) {
            throw new ServiceException("Invalid input number: " + e.getMessage());
        }
    } else {
        throw new ServiceException("Missing inputNumber or pattern.");
    }
    
    // Output
    IDataUtil.put(cursor, "formattedString", formattedString);
    cursor.destroy();
	
		// --- <<IS-END>> ---

                
	}



	public static final void isDoubleNaN (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(isDoubleNaN)>> ---
		// @sigtype java 3.5
		// [i] object:0:required inputNumber
		// [o] object:0:required isDoubleNaN
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	initInputNumber = IDataUtil.getString( pipelineCursor, "inputNumber" );
		pipelineCursor.destroy();
		
		Double inputNumber = parseToDouble(initInputNumber);
		Boolean isDoubleNaN = Double.isNaN(inputNumber);	
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "isDoubleNaN", isDoubleNaN );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void parseDouble (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(parseDouble)>> ---
		// @sigtype java 3.5
		// [i] object:0:required doubleInput
		// [o] object:0:required parseResult
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	init_doubleInput = IDataUtil.getString( pipelineCursor, "doubleInput" );
		pipelineCursor.destroy();
		
		double doubleInput = parseToDouble(init_doubleInput);
		
		String pattern1 = "##.#####";
		Locale locale = new Locale("en", "UK");
		
		DecimalFormat decimalFormat = (DecimalFormat) NumberFormat.getNumberInstance(locale);
		decimalFormat.applyPattern(pattern1);
		Double result = Double.parseDouble(decimalFormat.format(doubleInput));
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "parseResult", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void pokokHutangHelper (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(pokokHutangHelper)>> ---
		// @sigtype java 3.5
		// [i] object:0:required otr
		// [i] object:0:required dp
		// [i] object:0:required asuransi
		// [i] object:0:required administrasi
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	otrInput = IDataUtil.getString( pipelineCursor, "otr" );
		String	dpInput = IDataUtil.getString( pipelineCursor, "dp" );
		String	asuransiInput = IDataUtil.getString( pipelineCursor, "asuransi" );
		String	administrasiInput = IDataUtil.getString( pipelineCursor, "administrasi" );
		pipelineCursor.destroy();
		
		double otr = parseToDouble(otrInput);
		double dp = parseToDouble(dpInput);
		double asuransi = parseToDouble(asuransiInput);
		double administrasi = parseToDouble(administrasiInput);
		
		double calculateResult = otr - (dp + asuransi + administrasi);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", calculateResult );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void roundAt (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(roundAt)>> ---
		// @sigtype java 3.5
		// [i] object:0:required n
		// [i] object:0:required p
		// [o] object:0:required result
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String	init_n = IDataUtil.getString( pipelineCursor, "n" );
		String	init_p = IDataUtil.getString( pipelineCursor, "p" );
		pipelineCursor.destroy();
		
		double n = parseToDouble(init_n);
		int p = parseToInteger(init_p);
		
		double result = 0;
		double s = Math.pow(10, p);
		result = Math.round(n * s) / s;
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void roundDouble (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(roundDouble)>> ---
		// @sigtype java 3.5
		// [i] object:0:required doubleInput
		// [o] object:0:required doubleOutput
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
		String init_doubleInput = IDataUtil.getString( pipelineCursor, "doubleInput" );
		pipelineCursor.destroy();
		
		double doubleInput = parseToDouble(init_doubleInput);
		
		double result = Math.round(doubleInput);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		Object doubleOutput = new Object();
		IDataUtil.put( pipelineCursor_1, "doubleOutput", result );
		pipelineCursor_1.destroy();
			
		// --- <<IS-END>> ---

                
	}



	public static final void stringToInteger (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(stringToInteger)>> ---
		// @sigtype java 3.5
		// [i] field:0:required inputString
		// [o] object:0:required result
		
		// pipeline
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	inputString = IDataUtil.getString( pipelineCursor, "inputString" );
		pipelineCursor.destroy();
		
		Integer result = Integer.parseInt(inputString);
		
		// pipeline
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		IDataUtil.put( pipelineCursor_1, "result", result );
		pipelineCursor_1.destroy();
		// --- <<IS-END>> ---

                
	}

	// --- <<IS-START-SHARED>> ---
	public static double roundAt(double n, int p) {
		double s = Math.pow(10, p);
		return (Math.round(n * s) / s);
	}
	
	public static double round7(double a) {
		double b = a / 10000000.0;
		double value = b * 10000000.0;
		return value;
	}
	
	public static double financePmt(double r, int nper, double pv, double fv, int type) {
	    double pmt = -r * (pv * Math.pow(1.0 + r, (double)nper) + fv) / ((1.0 + r * (double)type) * (Math.pow(1.0 + r, (double)nper) - 1.0));
	    return pmt;
	}
	
	public static double roundUpRibuan(double a) {
		double b = a / 1000;
		double value = Math.ceil(b) * 1000;
		return value;
	}
	
	private static double parseToDouble(String value) {
	    try {
	        return Double.parseDouble(value);
	    } catch (Exception e) {
	        return 0;
	    }
	}
	
	private static Integer parseToInteger(String value) {
	    try {
	        return Integer.parseInt(value);
	    } catch (Exception e) {
	        return 0;
	    }
	}
		
	// --- <<IS-END-SHARED>> ---
}

