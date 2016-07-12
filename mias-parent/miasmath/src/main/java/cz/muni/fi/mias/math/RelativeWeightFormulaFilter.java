/*
 * Copyright 2016 MIR@MU Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package cz.muni.fi.mias.math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * Formula filters deciding whether given {@link Formula} should be used or not.
 *
 * The decision is base on comparison of the weight the given formula with the
 * weight of the original formula this formula was derived from.
 *
 * @author Michal Růžička
 */
public class RelativeWeightFormulaFilter implements FormulaFilter {

    /**
     * Class activity logger.
     */
    private static final Logger LOG = LogManager.getLogger(RelativeWeightFormulaFilter.class);

    /**
     * Threshold of filtering. The formula passes the filter if
     * {@code (formula.getWeight() / formula.getOriginalFormulaWeight()) >= filteringThreshold}.
     */
    private final float filteringThreshold;

    /**
     * Instantiate formula filter letting to pass any formula with relative
     * weight greater or equal to {@code filteringThreshold} comparing to the
     * weight of the original formula this formula was derived from.
     *
     * Technically speaking, the formula passes the filter and should be used if
     * {@code (formula.getWeight() / formula.getOriginalFormulaWeight()) >= filteringThreshold}
     * holds.
     *
     * @param filteringThreshold Filtering threshold of this instance. This
     * value is final for the instance and cannot be changed later. Small values
     * let pass more formulae: Setting {@code filteringThreshold} to 0.0
     * disables filtering at all and all formulae will pass. Setting it to 100.0
     * will let through only the original formulae, any derived formulae with
     * smaller weights will be rejected.
     */
    public RelativeWeightFormulaFilter(float filteringThreshold) {
        if (filteringThreshold < 0.0f || filteringThreshold > 1.0f) {
            throw new IllegalArgumentException(
                    "Invalid threshold '"
                    + filteringThreshold
                    + "': value between 0.0f and 1.0f is required.");
        }
        this.filteringThreshold = filteringThreshold;
    }

    /**
     * Decide whether given {@code formula} should be used or not.
     *
     * The decision is base on comparison of the weight the given formula with
     * the weight of the original formula this formula was derived from.
     * Formulae with weight too small comparing to the weight of the formula
     * they originates from are filtered (should not be used).
     *
     * Technically speaking, the formula passes the filter and should be used if
     * {@code (formula.getWeight() / formula.getOriginalFormulaWeight()) >= filteringThreshold}
     * holds. Filtering threshold {@link #filteringThreshold} is set at the time
     * of filter construction and cannot be changed but the set value can be
     * checked via {@link #getFilteringThreshold()} method.
     *
     * @param formula {@link Formula} to be judged.
     * @return {@code true} (the formula should be used) if the relative formula
     * weight comparing to the original formula weight is high (higher than set
     * filtering threshold). {@code false} otherwise.
     */
    @Override
    public boolean passes(Formula formula) {
        boolean result = (formula.getWeight() / formula.getOriginalFormulaWeight()) >= filteringThreshold;
        LOG.debug("Formula weight {}, original formula weight {}, filtering threshold {} – result: {}",
                formula.getWeight(), formula.getOriginalFormulaWeight(), filteringThreshold, result);
        return result;
    }

    /**
     * Returns filtering threshold set for this instance.
     *
     * The value depends on the constant given on construction of the instance
     * and cannot be changed.
     *
     * @return Filtering threshold set for this instance.
     * @see #passes(cz.muni.fi.mias.math.Formula)
     */
    public float getFilteringThreshold() {
        return this.filteringThreshold;
    }

}
