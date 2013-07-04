/**
 * Copyright (c) 2013 Armin Töpfer
 *
 * This file is part of ProfileSim.
 *
 * ProfileSim is free software: you can redistribute it and/or modify it under
 * the terms of the GNU General Public License as published by the Free Software
 * Foundation, either version 3 of the License, or any later version.
 *
 * ProfileSim is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU General Public License for more
 * details.
 *
 * You should have received a copy of the GNU General Public License along with
 * ProfileSim. If not, see <http://www.gnu.org/licenses/>.
 */
package ch.ethz.bsse.profilesim.utils;

import ch.ethz.bsse.profilesim.informationholder.Globals;
import ch.ethz.bsse.profilesim.informationholder.Read;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Armin Töpfer (armin.toepfer [at] gmail.com)
 */
public class CreateProfile {

    private int L;
    private int genomeCount;

    public CreateProfile(String genomePath, String alignmentPath) {
        //parse Genomes into packed byte arrays
        byte[][] genomeMSA = Utils.splitReadsIntoByteArrays(FastaParser.parseFarFile(genomePath));
        //assume that all genomes have the same length
        //get genome length
        L = BitMagic.getLength(genomeMSA[0]);
        //number of genomes
        genomeCount = genomeMSA.length;

        Map<Integer, Byte> genomePosToBase = new HashMap<>();
        int hetero = 0;
        for (int i = 0; i < L; i++) {
            boolean homogeneous = true;
            for (int j = 1; j < 5; j++) {
                byte prior = BitMagic.getPosition(genomeMSA[j - 1], i);
                byte current = BitMagic.getPosition(genomeMSA[j], i);
                if (prior != current) {
                    homogeneous = false;
                    break;
                }
            }
            if (homogeneous) {
                genomePosToBase.put(i, BitMagic.getPosition(genomeMSA[0], i));
            } else {
                hetero++;
            }
        }
        StatusUpdate.getINSTANCE().println("Heterogenous positions " + hetero);
        StatusUpdate.getINSTANCE().println("Homogenous positions\t" + genomePosToBase.size() + "\n");

        List<Map<Double, Map<String, Integer>>> matrices = new ArrayList<>();

        for (int i = 0; i < 250; i++) {
            matrices.add(new HashMap<Double, Map<String, Integer>>());
        }
        Globals.getINSTANCE().setUNPAIRED(true);
        Map<String, Read> alignmentReads = Utils.parseBAMSAMPure(alignmentPath);
        int skipped = 0;
        
        for (Read r : alignmentReads.values()) {
            int l = r.getLength() > 250 ? 250 : r.getLength();
            for (int j = 0; j < l; j++) {
                if (!matrices.get(j).containsKey(r.getQuality(j))) {
                    matrices.get(j).put(r.getQuality(j), createMatrix());
                }
                Map<String, Integer> matrix = matrices.get(j).get(r.getQuality(j));
                int global = r.getWatsonBegin() + j;
                if (genomePosToBase.containsKey(global)) {
                    String conversion = Utils.reverse(genomePosToBase.get(global)) + "->" + Utils.reverse(r.getBase(j));
                    matrix.put(conversion, matrix.get(conversion) + 1);
                }
            }
        }
        StatusUpdate.getINSTANCE().println("Reads\t\t\t" + alignmentReads.size());
        StatusUpdate.getINSTANCE().println("Reads>250bp\t\t" + skipped);
        System.out.println("");
        StringBuilder sb = new StringBuilder();
        sb.append("#Pos\tPhred");
        for (int b = 0; b < 4; b++) {
            for (int v = 0; v < 5; v++) {
                sb.append("\t").append(Utils.reverse(b)).append("->").append(Utils.reverse(v));
            }
        }
        sb.append("\n");
        for (int j = 0; j < 250; j++) {
            Double[] a = matrices.get(j).keySet().toArray(new Double[matrices.get(j).keySet().size()]);
            Arrays.sort(a);
            for (Double q : a) {
                int phred = (int) (-10 * Math.log10(1 - q));
                sb.append(j).append("\t").append(phred);
                for (int b = 0; b < 4; b++) {
                    for (int v = 0; v < 5; v++) {
                        Integer count = matrices.get(j).get(q).get(Utils.reverse(b) + "->" + Utils.reverse(v));
                        sb.append("\t").append(count);
                    }
                }
                sb.append("\n");
            }
        }
        Utils.saveFile(Globals.getINSTANCE().getSAVEPATH() + "profile.txt", sb.toString());
    }

    private Map<String, Integer> createMatrix() {
        Map<String, Integer> map = new HashMap<>();
        for (int b = 0; b < 5; b++) {
            for (int v = 0; v < 5; v++) {
                map.put(Utils.reverse(b) + "->" + Utils.reverse(v), 0);
            }
        }
        return map;
    }
}
