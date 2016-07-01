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

/**
 * Interface of formula filters deciding whether given {@link Formula} should be
 * used or not.
 *
 * The decision can be used on various factors such as comparison of the formula
 * weight and weight of the original formula this formula was derived from, for
 * example.
 *
 * @author Michal Růžička
 */
public interface FormulaFilter {

    /**
     * Decide whether given {@code formula} should be used or not.
     *
     * The decision can be used on various factors such as comparison of the
     * formula weight and weight of the original formula this formula was
     * derived from, for example.
     *
     * @param formula {@link Formula} to be judged.
     * @return <em>{@code true} if</em> the formula meets the judged criterion,
     * passes the filtering and <em>should be used</em>, {@code false}
     * otherwise.
     */
    boolean passes(Formula formula);

}
