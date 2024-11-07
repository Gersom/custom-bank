package gersom.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class MoneyUtils {
    /**
     * Redondea un valor double a 2 decimales
     * @param value Valor a redondear
     * @return Valor redondeado a 2 decimales
     */
    public static double roundToTwoDecimals(double value) {
        return BigDecimal.valueOf(value)
            .setScale(2, RoundingMode.HALF_UP)
            .doubleValue();
    }

    /**
     * Formatea un valor double a String con 2 decimales
     * @param value Valor a formatear
     * @return String formateado con 2 decimales
     */
    public static String formatMoney(double value) {
        return String.format("%.2f", roundToTwoDecimals(value));
    }
}