package ucu.edu.aed.medible.lib;

public class Formatter {
    private static final String[] SI = {"B", "kB", "MB", "GB", "TB", "PB", "EB"};
    private static final String[] IEC = {"B", "KiB", "MiB", "GiB", "TiB", "PiB", "EiB"};

    private static final class UnitDef {
        final String name;
        final long factorNs;
        final boolean showDecimals;

        UnitDef(String name, long factorNs, boolean showDecimals) {
            this.name = name;
            this.factorNs = factorNs;
            this.showDecimals = showDecimals;
        }
    }

    private static final UnitDef[] UNITS = new UnitDef[]{
            new UnitDef("ns", 1L, false),
            new UnitDef("µs", 1_000L, true),
            new UnitDef("ms", 1_000_000L, true),
            new UnitDef("s", 1_000_000_000L, true),
            new UnitDef("min", 60_000_000_000L, true),
            new UnitDef("h", 3_600_000_000_000L, true),
            new UnitDef("d", 86_400_000_000_000L, true)
    };

    public static String formatMemory(long bytes, boolean si, int decimals) {
        double unit = si ? 1000.0 : 1024.0;
        String[] suffix = si ? SI : IEC;

        if (bytes == Long.MIN_VALUE) return formatMemory(Long.MIN_VALUE + 1, si, decimals);
        String sign = bytes < 0 ? "-" : "";
        long abs = Math.abs(bytes);

        if (abs < unit) return sign + abs + " B";

        int exp = (int) Math.min((Math.log(abs) / Math.log(unit)), suffix.length - 1);
        double value = abs / Math.pow(unit, exp);
        //noinspection MalformedFormatString
        return String.format("%s%." + decimals + "f %s", sign, value, suffix[exp]);
    }

    public static String formatMemory(long bytes) {
        return formatMemory(bytes, true, 2);
    }

    public static String formatNanos(long nanos, int decimals) {
        if (nanos == Long.MIN_VALUE) return formatNanos(Long.MIN_VALUE + 1, decimals);
        String sign = nanos < 0 ? "-" : "";
        long abs = Math.abs(nanos);

        UnitDef chosen;
        if (abs < 1_000L) {
            chosen = UNITS[0]; // ns
        } else {
            chosen = UNITS[0];
            for (int i = UNITS.length - 1; i >= 0; i--) {
                if (abs >= UNITS[i].factorNs) {
                    chosen = UNITS[i];
                    break;
                }
            }
        }

        if (chosen.showDecimals) {
            double value = abs / (double) chosen.factorNs;
            //noinspection MalformedFormatString
            return String.format("%s%." + decimals + "f %s", sign, value, chosen.name);
        } else {
            return String.format("%s%d %s", sign, abs, chosen.name);
        }
    }
}
