package com.sam.demo.util;



import com.sam.demo.enums.NumStrFormatEnum;
import com.sam.demo.exception.BaseException;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.regex.Pattern;


public class NumberUtils {
    /**
     * The constant NUM_STR_NA.
     */
    public static final String NUM_STR_NA = "N/A";
    /**
     * The constant NUM_STR_BLANK.
     */
    public static final String NUM_STR_BLANK = "";
    /**
     * The constant NUM_STR_DIGITAL_REGEX.
     */
    public static final String NUM_STR_DIGITAL_REGEX = "^(\\-)?(\\d)+(\\.(\\d)+)?$";

    /**
     * Trans to double double.
     *
     * @param <T>    the type parameter
     * @param number the number
     * @return the double
     */
    public static <T> Double transToDouble(T number) {
        return transToDouble(number, 2);
    }

    /**
     * Trans to double double.
     *
     * @param <T>      the type parameter
     * @param number   the number
     * @param newScale the new scale
     * @return the double
     */
    public static <T> Double transToDouble(T number, int newScale) {
        return transToDouble(number, newScale, BigDecimal.ROUND_UP);
    }

    /**
     * Trans to double double.
     *
     * @param <T>       the type parameter
     * @param number    the number
     * @param newScale  the new scale
     * @param roundMode the round mode
     * @return the double
     */
    public static <T> Double transToDouble(T number, int newScale, int roundMode) {
        BigDecimal bigDecimal = transToDecimal(number);
        return bigDecimal.setScale(newScale, roundMode).doubleValue();
    }

    /**
     * Trans to int integer.
     *
     * @param <T>    the type parameter
     * @param number the number
     * @return the integer
     */
    public static <T> Integer transToInt(T number) {
        return transToInt(number, BigDecimal.ROUND_UP);
    }

    /**
     * Trans to int integer.
     *
     * @param <T>       the type parameter
     * @param number    the number
     * @param roundMode the round mode
     * @return the integer
     */
    public static <T> Integer transToInt(T number, int roundMode) {
        return transToDouble(number, 0, roundMode).intValue();
    }


    /**
     * Trans to num str string.
     *
     * @param <T>    the type parameter
     * @param number the number
     * @return the string
     */
    public static <T> String transToNumStr(T number) {
        return transToNumStr(number, 2);
    }


    /**
     * Trans to num str string.
     *
     * @param <T>      the type parameter
     * @param number   the number
     * @param newScale the new scale
     * @return the string
     */
    public static <T> String transToNumStr(T number, int newScale) {
        return transToNumStr(number, newScale, BigDecimal.ROUND_UP, NumStrFormatEnum.DIGITAL);
    }

    /**
     * Trans to num str string.
     *
     * @param <T>              the type parameter
     * @param number           the number
     * @param numStrFormatEnum the num str format enum
     * @return the string
     */
    public static <T> String transToNumStr(T number, NumStrFormatEnum numStrFormatEnum) {
        return transToNumStr(number, 2, BigDecimal.ROUND_UP, numStrFormatEnum);
    }

    /**
     * Trans to num str string.
     *
     * @param <T>       the type parameter
     * @param number    the number
     * @param newScale  the new scale
     * @param roundMode the round mode
     * @return the string
     */
    public static <T> String transToNumStr(T number, int newScale, int roundMode) {
        return transToNumStr(number, newScale, roundMode, NumStrFormatEnum.DIGITAL);

    }

    /**
     * Trans to num str string.
     *
     * @param <T>              the type parameter
     * @param number           the number
     * @param newScale         the new scale
     * @param roundMode        the round mode
     * @param numStrFormatEnum the num str format enum
     * @return the string
     */
    public static <T> String transToNumStr(T number, int newScale, int roundMode, NumStrFormatEnum numStrFormatEnum) {
        Double doubleValue = transToDouble(number, newScale, roundMode);
        if (doubleValue == 0D) {
            return formatZeroOrNullNumStr(numStrFormatEnum, newScale);
        } else {
            return formatNumStr(doubleValue, newScale);
        }
    }


    /**
     * Num str is spec zero boolean.
     *
     * @param numStr the num str
     * @return the boolean
     */
    public static boolean numStrIsSpecZero(String numStr) {
        return NUM_STR_NA.equals(numStr) || NUM_STR_BLANK.equals(numStr);
    }


    /**
     * Num str is digital boolean.
     *
     * @param numStr the num str
     * @return the boolean
     */
    public static boolean numStrIsDigital(String numStr) {
        Pattern digitalPattern = Pattern.compile(NUM_STR_DIGITAL_REGEX);
        return digitalPattern.matcher(numStr).find();
    }


    /**
     * Trans to decimal big decimal.
     *
     * @param <T>    the type parameter
     * @param number the number
     * @return the big decimal
     */
    public static <T> BigDecimal transToDecimal(T number) {
        if (number == null) {
            return BigDecimal.ZERO;
        } else if (Integer.class.isInstance(number)) {
            return new BigDecimal(Integer.class.cast(number));
        } else if (Double.class.isInstance(number)) {
            return BigDecimal.valueOf(Double.class.cast(number));
        } else if (Long.class.isInstance(number)) {
            return new BigDecimal(Long.class.cast(number));
        } else if (String.class.isInstance(number)) {
            return transStringToDecimal(number);
        } else if (BigDecimal.class.isInstance(number)) {
            return BigDecimal.class.cast(number);
        } else {
            throw new RuntimeException();
        }
    }


    private static <T> BigDecimal transStringToDecimal(T number) {
        if (numStrIsDigital(number.toString())) {
            try {
                return transToDecimal(DecimalFormat.getInstance().parse(number.toString()));
            } catch (ParseException e) {
                //throw new BaseException(String.format("String trans to Decimal error, source string=%s", number));
            }
        } else if (numStrIsSpecZero(number.toString())) {
            return BigDecimal.ZERO;
        } else {
            //throw new BaseException(String.format("String trans to Decimal error, source string=%s", number));
        }
        return  null;
    }


    private static String formatNumStr(Double doubleValue, int newScale) {
        DecimalFormat decimalFormat = new DecimalFormat();
        StringBuilder pattern = new StringBuilder();
        for (int i = 0; i <= newScale; i++) {
            if (i == 1) {
                pattern.append(".");
            }
            pattern.append("0");
        }
        decimalFormat.applyPattern(pattern.toString());
        return decimalFormat.format(doubleValue);
    }

    private static String formatZeroOrNullNumStr(NumStrFormatEnum numStrFormatEnum, int newScale) {
        if (NumStrFormatEnum.BLANK.equals(numStrFormatEnum)) {
            return NUM_STR_BLANK;
        } else if (NumStrFormatEnum.NA.equals(numStrFormatEnum)) {
            return NUM_STR_NA;
        } else {
            return formatNumStr(0d, newScale);
        }
    }
}
