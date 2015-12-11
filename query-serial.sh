#!/bin/bash

wsurl='http://localhost:8084/WebMIaS/ws/search'
index='0'
limit='1000'


query="$(cat <<EOQ

    <math>
              <mrow id="m64.1.17.pmml" xref="m64.1.17">
                <mrow id="m64.1.17.1.pmml" xref="m64.1.17.1">
                  <mover accent="true" id="m64.1.1.pmml" xref="m64.1.1">
                    <mi id="m64.1.1.2.pmml" xref="m64.1.1.2">x</mi>
                    <mo id="m64.1.1.1.pmml" xref="m64.1.1.1">˙</mo>
                  </mover>
                  <mo id="m64.1.17.1.1.pmml" xref="m64.1.17.1.1">⁢</mo>
                  <mrow id="m64.1.3.pmml" xref="m64.1.3">
                    <mo id="m64.1.3a.pmml" xref="m64.1.3">(</mo>
                    <mi id="m64.1.3b.pmml" xref="m64.1.3">t</mi>
                    <mo id="m64.1.3c.pmml" xref="m64.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m64.1.5.pmml" xref="m64.1.5">=</mo>
                <mrow id="m64.1.17.2.pmml" xref="m64.1.17.2">
                  <mrow id="m64.1.17.2.1.pmml" xref="m64.1.17.2.1">
                    <mi>A</mi>
                    <mo id="m64.1.17.2.1.1.pmml" xref="m64.1.17.2.1.1">⁢</mo>
                    <mi id="m64.1.7.pmml" xref="m64.1.7">x</mi>
                    <mo id="m64.1.17.2.1.1a.pmml" xref="m64.1.17.2.1.1">⁢</mo>
                    <mrow id="m64.1.9.pmml" xref="m64.1.9">
                      <mo id="m64.1.9a.pmml" xref="m64.1.9">(</mo>
                      <mi id="m64.1.9b.pmml" xref="m64.1.9">t</mi>
                      <mo id="m64.1.9c.pmml" xref="m64.1.9">)</mo>
                    </mrow>
                  </mrow>
                  <mo id="m64.1.11.pmml" xref="m64.1.11">+</mo>
                  <mrow id="m64.1.17.2.2.pmml" xref="m64.1.17.2.2">
                    <mi>B</mi>
                    <mo id="m64.1.17.2.2.1.pmml" xref="m64.1.17.2.2.1">⁢</mo>
                    <mi id="m64.1.13.pmml" xref="m64.1.13">u</mi>
                    <mo id="m64.1.17.2.2.1a.pmml" xref="m64.1.17.2.2.1">⁢</mo>
                    <mrow id="m64.1.15.pmml" xref="m64.1.15">
                      <mo id="m64.1.15a.pmml" xref="m64.1.15">(</mo>
                      <mi id="m64.1.15b.pmml" xref="m64.1.15">t</mi>
                      <mo id="m64.1.15c.pmml" xref="m64.1.15">)</mo>
                    </mrow>
                  </mrow>
                </mrow>
              </mrow>
            </math> <math>
            <apply id="m64.1.17" xref="m64.1.17.pmml">
              <eq id="m64.1.5" xref="m64.1.5.pmml"/>
              <apply id="m64.1.17.1" xref="m64.1.17.1.pmml">
                <times id="m64.1.17.1.1" xref="m64.1.17.1.1.pmml"/>
                <apply id="m64.1.1" xref="m64.1.1.pmml">
                  <ci id="m64.1.1.1" xref="m64.1.1.1.pmml">normal-˙</ci>
                  <ci id="m64.1.1.2" xref="m64.1.1.2.pmml">x</ci>
                </apply>
                <ci id="m64.1.3" xref="m64.1.3.pmml">t</ci>
              </apply>
              <apply id="m64.1.17.2" xref="m64.1.17.2.pmml">
                <plus id="m64.1.11" xref="m64.1.11.pmml"/>
                <apply id="m64.1.17.2.1" xref="m64.1.17.2.1.pmml">
                  <times id="m64.1.17.2.1.1" xref="m64.1.17.2.1.1.pmml"/>
                  <ci>A</ci>
                  <ci id="m64.1.7" xref="m64.1.7.pmml">x</ci>
                  <ci id="m64.1.9" xref="m64.1.9.pmml">t</ci>
                </apply>
                <apply id="m64.1.17.2.2" xref="m64.1.17.2.2.pmml">
                  <times id="m64.1.17.2.2.1" xref="m64.1.17.2.2.1.pmml"/>
                  <ci>B</ci>
                  <ci id="m64.1.13" xref="m64.1.13.pmml">u</ci>
                  <ci id="m64.1.15" xref="m64.1.15.pmml">t</ci>
                </apply>
              </apply>
            </apply>


          </math> "pole" "state" "eigenstructure"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ
        <math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "feedback"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

        <math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "feedback" "assigment"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

    <math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "feedback" "eigenstructure"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "feedback" "eigenstructure" "assigment"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "eigenstructure assigment" "feedback"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "eigenstructure assigment" "feedback" "assigment"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "eigenstructure assigment" "feedback" "eigenstructure"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "eigenstructure assigment" "feedback" "eigenstructure" "assigment"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "state feedback" "feedback"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "state feedback" "feedback" "eigenstructure"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "state feedback" "feedback" "eigenstructure" "assigment"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "state feedback" "eigenstructure assigment" "feedback"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "state feedback" "eigenstructure assigment" "feedback" "assigment"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "state feedback" "eigenstructure assigment" "feedback" "eigenstructure"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "state feedback" "eigenstructure assigment" "feedback" "eigenstructure" "assigment"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>
              <mrow id="m67.1.8.pmml" xref="m67.1.8">
                <mrow id="m67.1.8.1.pmml" xref="m67.1.8.1">
                  <mi id="m67.1.1.pmml" xref="m67.1.1">u</mi>
                  <mo id="m67.1.8.1.1.pmml" xref="m67.1.8.1.1">⁢</mo>
                  <mrow id="m67.1.3.pmml" xref="m67.1.3">
                    <mo id="m67.1.3a.pmml" xref="m67.1.3">(</mo>
                    <mi id="m67.1.3b.pmml" xref="m67.1.3">t</mi>
                    <mo id="m67.1.3c.pmml" xref="m67.1.3">)</mo>
                  </mrow>
                </mrow>
                <mo id="m67.1.5.pmml" xref="m67.1.5">∈</mo>
                <msup id="m67.1.8.2.pmml" xref="m67.1.8.2">
                  <mi id="m67.1.6.pmml" xref="m67.1.6">ℝ</mi>
                  <mi>m</mi>
                </msup>
              </mrow>
            </math> <math>
            <apply id="m67.1.8" xref="m67.1.8.pmml">
              <in id="m67.1.5" xref="m67.1.5.pmml"/>
              <apply id="m67.1.8.1" xref="m67.1.8.1.pmml">
                <times id="m67.1.8.1.1" xref="m67.1.8.1.1.pmml"/>
                <ci id="m67.1.1" xref="m67.1.1.pmml">u</ci>
                <ci id="m67.1.3" xref="m67.1.3.pmml">t</ci>
              </apply>
              <apply id="m67.1.8.2" xref="m67.1.8.2.pmml">
                <csymbol cd="ambiguous" id="m67.1.8.2.1">superscript</csymbol>
                <ci id="m67.1.6" xref="m67.1.6.pmml">ℝ</ci>
                <ci>m</ci>
              </apply>
            </apply>
            
            
          </math> "pole placement" "feedback"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo
