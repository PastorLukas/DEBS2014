package cz.muni.fi.sbapr.debs2014.csv;

import cz.muni.fi.sbapr.debs2014.event.SensorEvent;
import java.io.Closeable;
import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
import java.math.MathContext;
import java.nio.CharBuffer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author Lukáš Pástor
 */
public class CSVReader implements Closeable {

    private static final Logger LOG = LoggerFactory.getLogger(CSVReader.class);
    private static final char DEFAULT_DELIMITER = ',';    
    private static final int DEFAULT_LINE_LENGTH = 80;
        
    private BufferedLineReader reader = null;
    private CharBuffer lineBuffer = null;
    SensorEvent event = null;    
    private char delimiter;
          
    public CSVReader(Reader in) throws IOException {
        this(in, DEFAULT_LINE_LENGTH, DEFAULT_DELIMITER);
    }
    
    public CSVReader(Reader in, char delim) throws IOException {
        this(in, DEFAULT_LINE_LENGTH, delim);
    }
        
    public CSVReader(Reader in, int lineBufferLength) { 
         this(in, lineBufferLength, DEFAULT_DELIMITER);
    }
    
    public CSVReader(Reader in, int lineBufferLength, char delim) {        
        if (lineBufferLength <= 0)
            throw new IllegalArgumentException("Line buffer size <= 0");    
        
        reader = new BufferedLineReader(in);
        lineBuffer = CharBuffer.allocate(lineBufferLength);  
        event = new SensorEvent(); 
        delimiter = delim;    
    }    
    
    @Override
    public void close() throws IOException {        
       close(reader);
    }                  
    
    private Boolean hasNextLine() {    
        try {
            return reader.readLine(lineBuffer) != -1;           
        } catch (IOException ex) {
            LOG.info(ex.getMessage());
            closeQuietly(reader);
        }
        return false;
    }
     
    public SensorEvent getSensorEvent() {        
        if (!hasNextLine()) {
            return null;
        }
        
        char[] line = lineBuffer.array();
        int start = 0;
        int end = 0;     
                

        //  id                
        if ((end = indexOf(line, delimiter, start)) == -1) {
            LOG.info("ERROR PARSING ID");
            return null;
        }
        event.setId(parseLong(line, start, end - start));

        //  timestamp
        start = end + 1;
        if ((end = indexOf(line, delimiter, start)) == -1) {
            LOG.info("ERROR PARSING TIMESTAMP");
            return null;
        }
        event.setTimestamp(parseLong(line, start, end - start));

        //  value
        start = end + 1;
        if ((end = indexOf(line, delimiter, start)) == -1) {
            LOG.info("ERROR PARSING VALUE");
            return null;
        }                
        event.setValue(new BigDecimal(line, start, end - start, MathContext.DECIMAL32)); 
        
        //  property
        start = end + 1;
        if ((end = indexOf(line, delimiter, start)) == -1) {
            LOG.info("ERROR PARSING PROPERTY");
            return null;
        }
        event.setProperty(parseBoolean(line[start]));

        //  plugId
        start = end + 1;
        if ((end = indexOf(line, delimiter, start)) == -1) {
            LOG.info("ERROR PARSING PLUGID");
            return null;
        }
        event.setPlugId(parseLong(line, start, end - start));

        //  householdId
        start = end + 1;
        if ((end = indexOf(line, delimiter, start)) == -1) {
            LOG.info("ERROR PARSING HOUSEHOLD");
            return null;
        }
        event.setHouseholdId(parseLong(line, start, end - start));

        //  houseId
        start = end + 1;
        event.setHouseId(parseLong(line, start, lineBuffer.limit() - start));
        
        return event;
    }       
    
    public static int indexOf(char[] array, char valueToFind, int startIndex) {
        if (array == null) {
            return -1;
        }

        if (startIndex < 0) {
            startIndex = 0;
        }

        int length = array.length;
        for (int i = startIndex; i < length; i++)
            if (valueToFind == array[i])
                return i;                    
        
        return -1;
    }  
    
    
    private static long parseLong(char[] array, int offset, int length) throws NumberFormatException {        
        if (array == null) {
            throw new NumberFormatException("null");
        }      

        if (length < 0) {
            throw new ArrayIndexOutOfBoundsException(length);
        }
        if (offset < 0) {
            throw new ArrayIndexOutOfBoundsException(offset);
        }
        if (offset > array.length - length) {
            throw new ArrayIndexOutOfBoundsException(offset + length);
        }

        int radix = 10;
        long result = 0;
        boolean negative = false;
        int i = 0;
        int len = length;
        long limit = -Long.MAX_VALUE;
        long multmin;
        int digit;

        if (len > 0) {
            char firstChar = array[offset];
            if (firstChar < '0') { // Possible leading "+" or "-"
                if (firstChar == '-') {
                    negative = true;
                    limit = Long.MIN_VALUE;
                } else if (firstChar != '+') {
                    throw new NumberFormatException();
                }

                if (len == 1) // Cannot have lone "+" or "-"
                {
                    throw new NumberFormatException();
                }
                i++;
            }
            multmin = limit / radix;
            while (i < len) {
                // Accumulating negatively avoids surprises near MAX_VALUE
                digit = Character.digit(array[offset + i++], radix);
                if (digit < 0) {
                    throw new NumberFormatException();
                }
                if (result < multmin) {
                    throw new NumberFormatException();
                }
                result *= radix;
                if (result < limit + digit) {
                    throw new NumberFormatException();
                }
                result -= digit;
            }
        } else {
            throw new NumberFormatException();
        }
        return negative ? result : -result;
    }
    
    
    private static Boolean parseBoolean(char c) {
        return (c == '1') ? Boolean.TRUE : Boolean.FALSE;
    }            
    
    
    private static void ensureOpen(final Reader reader) throws IOException {
        if (reader == null) {
            throw new IOException("Stream closed");
        }
    }
    
    //  throw exception & clear reader after
    private static void close(Closeable closeable) throws IOException {
        try {
           tryClose(closeable); 
        } finally {
            closeable = null;
        }          
    }
    
    //  suppress exception & clear reader after
    private static void closeQuietly(Closeable closeable) {
        try {
            tryClose(closeable);
        } catch (IOException ioe) {
            // ignore
        } finally {
            closeable = null;
        }              
    }
    
    //  throw exception & DONT clear reader after
    private static void tryClose(final Closeable closeable) throws IOException {
        if (closeable != null) {
            closeable.close();
        }               
    }
    
    //  suppress exception & DONT clear reader after
    private static void tryCloseQuietly(final Closeable closeable) {
        try {
            tryClose(closeable);            
        } catch (IOException ioe) {
            // ignore
        }
    }                      
}