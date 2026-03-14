package KomiMsUserProfile;

// -----( IS Java Code Template v1.2

import com.wm.data.*;
import com.wm.util.Values;
import com.wm.app.b2b.server.Service;
import com.wm.app.b2b.server.ServiceException;
// --- <<IS-START-IMPORTS>> ---
import java.util.regex.Pattern;
// --- <<IS-END-IMPORTS>> ---

public final class utils

{
	// ---( internal utility methods )---

	final static utils _instance = new utils();

	static utils _newInstance() { return new utils(); }

	static utils _cast(Object o) { return (utils)o; }

	// ---( server methods )---




	public static final void validateRegex (IData pipeline)
        throws ServiceException
	{
		// --- <<IS-START(validateRegex)>> ---
		// @sigtype java 3.5
		// [i] field:0:required inputString
		// [i] field:0:required inputRegex
		// [o] object:0:required isValid
		IDataCursor pipelineCursor = pipeline.getCursor();
			String	inputString = IDataUtil.getString( pipelineCursor, "inputString" );
			String	inputRegex = IDataUtil.getString( pipelineCursor, "inputRegex" );
		pipelineCursor.destroy();
		
		boolean status = false;
		
		if (inputString != null && inputRegex != null) {
			status = Pattern.matches(inputRegex, inputString);
		}
		
		IDataCursor pipelineCursor_1 = pipeline.getCursor();
		Object isValid = new Object();
		IDataUtil.put( pipelineCursor_1, "isValid", status );
		pipelineCursor_1.destroy();
		
			
		// --- <<IS-END>> ---

                
	}
}

