public class Huntingtons {

    // Devuelve el número máximo de repeticiones consecutivas de "CAG" en la cadena de ADN.
    public static int maxRepeats(String dna) {
        int maxCount = 0;
        int currentCount = 0;

        for (int i = 0; i <= dna.length() - 3; i++) {
            String triplet = dna.substring(i, i + 3);
            if (triplet.equals("CAG")) {
                currentCount++;
                i += 2; // avanzar al siguiente triplete inmediatamente
            } else {
                if (currentCount > maxCount) maxCount = currentCount;
                currentCount = 0;
            }
        }
        if (currentCount > maxCount) maxCount = currentCount;
        return maxCount;
    }

    // Devuelve una copia de s con todos los espacios en blanco eliminados.
    public static String removeWhitespace(String s) {
        String t = s.replace("\n", "");
        t = t.replace("\t", "");
        t = t.replace(" ", "");
        return t;
    }

    // Devuelve el diagnóstico correspondiente al número máximo de repeticiones.
    public static String diagnose(int maxRepeats) {
        if (maxRepeats <= 9) return "no humano";
        else if (maxRepeats <= 35) return "normal";
        else if (maxRepeats <= 39) return "alto riesgo";
        else if (maxRepeats <= 180) return "Huntington";
        else return "no humano";
    }

    // Cliente de prueba.
    public static void main(String[] args) {
        In in = new In(args[0]);
        String dna = in.readAll();
        dna = removeWhitespace(dna);
        int max = maxRepeats(dna);
        StdOut.println("repeticiones máximas = " + max);
        StdOut.println(diagnose(max));
    }
}
