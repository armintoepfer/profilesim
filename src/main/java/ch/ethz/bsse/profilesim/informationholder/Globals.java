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
package ch.ethz.bsse.profilesim.informationholder;

import java.io.File;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Information holder for all necessary given and inferred parameters.
 *
 * @author Armin Töpfer (armin.toepfer [at] gmail.com)
 */
public class Globals {

    private static final Globals INSTANCE = new Globals();

    public static Globals getINSTANCE() {
        return INSTANCE;
    }
    private boolean GRADIENT;
    private boolean ANNEALING;
    private boolean NO_GAPS;
    private boolean MAX;
    private boolean COVERAGE;
    private boolean BOOTSTRAP;
    private boolean REFINEMENT;
    private boolean SAMPLE_PROTEINS;
    private boolean SAMPLE_READS;
    private boolean NO_QUALITY;
    private boolean WINDOW;
    private boolean UNPAIRED;
    private boolean USER_OPTIMUM;
    private boolean BIAS_MU;
    private boolean SILENT;
    private boolean STOP_QUICK;
    private boolean PRINT_ALIGNMENT;
    private boolean CIRCOS;
    private boolean NOSAMPLE;
    private boolean OVERLAP;
    private boolean UNINFORMATIVE_EPSILON_PRIOR;
    private boolean PDELTA;
    private boolean PLOT;
    private boolean STORAGE;
    private boolean SNAPSHOTS;
    private boolean FLAT_EPSILON_PRIOR;
    private boolean DEBUG;
    private boolean NO_RECOMB = false;
    private boolean FORCE_NO_RECOMB = false;
    private boolean PARALLEL_RESTARTS = false;
    private boolean LOG_BIC = false;
    private boolean LOGGING = false;
    private boolean PRINT = true;
    private boolean MODELSELECTION;
    private boolean PAIRED = false;
    private boolean PRIORMU;
    private boolean SPIKERHO;
    private double MAX_DEL;
    private double MAX_OVERALL_DEL;
    private double CUTOFF;
    private double PCHANGE;
    private double MULT_RHO;
    private double MULT_RHO_MIN;
    private double MULT_MU;
    private double MULT_MU_MIN;
    private double BETA_Z;
    private double ALPHA_Z;
    private double ALPHA_H;
    private double ESTIMATION_EPSILON;
    private double DELTA_LLH;
    private double DELTA_REFINE_LLH;
    private boolean PRUNE;
    private double INTERPOLATE_MU;
    private double INTERPOLATE_RHO;
    private double CURRENT_DELTA_LLH = 0;
    private double MAX_LLH = -1;
    private double MIN_BIC = Double.MIN_VALUE;
    private int READ_MINLENGTH;
    private int WINDOW_BEGIN;
    private int WINDOW_END;
    private int ALIGNMENT_BEGIN = Integer.MAX_VALUE;
    private int ALIGNMENT_END = Integer.MIN_VALUE;
    private int STEPS;
    private int DESIRED_REPEATS;
    private int SAMPLING_NUMBER;
    private int NREAL;
    private int REPEATS;
    private int K_MIN;
    private final int cpus = Runtime.getRuntime().availableProcessors();
    private List<Integer> runtime = new LinkedList<>();
    private String GENOME;
    private String OPTIMUM;
    private String SAVEPATH;
    private StringBuilder LOG = new StringBuilder();
    private final ForkJoinPool fjPool = new ForkJoinPool();
    private final AtomicInteger MERGED_COUNT = new AtomicInteger(0);
    private final AtomicInteger PAIRED_COUNT = new AtomicInteger(0);
    private int hammingMax = 0;

    public Globals getInstance() {
        return INSTANCE;
    }

    public synchronized void log(Object o) {
        if (PRINT) {
            System.out.print(o);
        } else {
            if (LOGGING) {
                LOG.append(o);
            }
        }
    }


    public int getALIGNMENT_BEGIN() {
        return ALIGNMENT_BEGIN;
    }

    public int getALIGNMENT_END() {
        return ALIGNMENT_END;
    }

    public String getSAVEPATH() {
        return SAVEPATH;
    }

    public boolean isLOGGING() {
        return LOGGING;
    }

    public boolean isPRINT() {
        return PRINT;
    }

    public void setALIGNMENT_BEGIN(int ALIGNMENT_BEGIN) {
        this.ALIGNMENT_BEGIN = ALIGNMENT_BEGIN;
    }

    public void setALIGNMENT_END(int ALIGNMENT_END) {
        this.ALIGNMENT_END = ALIGNMENT_END;
    }

    public void setSAVEPATH(String SAVEPATH) {
        this.SAVEPATH = SAVEPATH;
    }

    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
    }

    public void setLOGGING(boolean LOGGING) {
        this.LOGGING = LOGGING;
    }

    public void setPRINT(boolean PRINT) {
        this.PRINT = PRINT;
    }

    public void setLOG(StringBuilder LOG) {
        this.LOG = LOG;
    }

    public int getCpus() {
        return cpus;
    }

    public boolean isSTORAGE() {
        return STORAGE;
    }

    public void setSTORAGE(boolean STORAGE) {
        this.STORAGE = STORAGE;
    }

    public String getGENOME() {
        return GENOME;
    }

    public void setGENOME(String GENOME) {
        this.GENOME = GENOME;
    }

    public void setUNPAIRED(boolean UNPAIRED) {
        this.UNPAIRED = UNPAIRED;
    }

    public boolean isUNPAIRED() {
        return UNPAIRED;
    }

    public boolean isWINDOW() {
        return WINDOW;
    }

    public void setWINDOW(boolean WINDOW) {
        this.WINDOW = WINDOW;
    }

    public int getWINDOW_BEGIN() {
        return WINDOW_BEGIN;
    }

    public void setWINDOW_BEGIN(int WINDOW_BEGIN) {
        this.WINDOW_BEGIN = WINDOW_BEGIN;
    }

    public int getWINDOW_END() {
        return WINDOW_END;
    }

    public void setWINDOW_END(int WINDOW_END) {
        this.WINDOW_END = WINDOW_END;
    }
}
