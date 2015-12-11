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
              <mrow id="m51.1.15.pmml" xref="m51.1.15">                          
                <mrow id="m51.1.15.1.pmml" xref="m51.1.15.1">                    
                  <mi id="m51.1.1.pmml" xref="m51.1.1">ρ</mi>                    
                  <mo id="m51.1.15.1.1.pmml" xref="m51.1.15.1.1">⁢</mo>          
                  <mrow id="m51.1.3.pmml" xref="m51.1.3">                        
                    <mo id="m51.1.3a.pmml" xref="m51.1.3">(</mo>                 
                    <mi>A</mi>                                                   
                    <mo id="m51.1.3c.pmml" xref="m51.1.3">)</mo>                 
                  </mrow>                                                        
                </mrow>                                                          
                <mo id="m51.1.5.pmml" xref="m51.1.5">=</mo>                      
                <mrow id="m51.1.15.2.pmml" xref="m51.1.15.2">                    
                  <munder id="m51.1.15.2.1.pmml" xref="m51.1.15.2.1">            
                    <mo movablelimits="false" id="m51.1.6.pmml" xref="m51.1.6">lim</mo>
                    <mrow id="m51.1.7.1.pmml" xref="m51.1.7.1">                  
                      <mi id="m51.1.7.1.1.pmml" xref="m51.1.7.1.1">n</mi>        
                      <mo id="m51.1.7.1.2.pmml" xref="m51.1.7.1.2">→</mo>        
                      <mi mathvariant="normal" id="m51.1.7.1.3.pmml" xref="m51.1.7.1.3">∞</mi>
                    </mrow>                                                      
                  </munder>                                                      
                  <mo id="m51.1.15.2a.pmml" xref="m51.1.15.2">⁡</mo>             
                  <msup id="m51.1.15.2.2.pmml" xref="m51.1.15.2.2">              
                    <mrow id="m51.1.15.2.2.2.pmml" xref="m51.1.15.2.2.2">        
                      <mo fence="true" id="m51.1.15.2.2.2a.pmml" xref="m51.1.15.2.2.2">||</mo>
                      <msup id="m51.1.15.2.2.2.2.pmml" xref="m51.1.15.2.2.2.2">  
                        <mi>A</mi>                                               
                        <mi id="m51.1.11.1.pmml" xref="m51.1.11.1">n</mi>        
                      </msup>                                                    
                      <mo fence="true" id="m51.1.15.2.2.2b.pmml" xref="m51.1.15.2.2.2">||</mo>
                    </mrow>                                                      
                    <mrow id="m51.1.14.1.pmml" xref="m51.1.14.1">                
                      <mn id="m51.1.14.1.1.pmml" xref="m51.1.14.1.1">1</mn>      
                      <mo id="m51.1.14.1.2.pmml" xref="m51.1.14.1.2">/</mo>      
                      <mi id="m51.1.14.1.3.pmml" xref="m51.1.14.1.3">n</mi>      
                    </mrow>                                                      
                  </msup>                                                        
                </mrow>                                                          
              </mrow>                                                            
            </math> <math>                                                       
            <apply id="m51.1.15" xref="m51.1.15.pmml">                           
              <eq id="m51.1.5" xref="m51.1.5.pmml"/>                             
              <apply id="m51.1.15.1" xref="m51.1.15.1.pmml">                     
                <times id="m51.1.15.1.1" xref="m51.1.15.1.1.pmml"/>              
                <ci id="m51.1.1" xref="m51.1.1.pmml">ρ</ci>                      
                <ci>A</ci>                                                       
              </apply>                                                           
              <apply id="m51.1.15.2" xref="m51.1.15.2.pmml">                     
                <apply id="m51.1.15.2.1" xref="m51.1.15.2.1.pmml">               
                  <csymbol cd="ambiguous" id="m51.1.15.2.1.1">subscript</csymbol>
                  <limit id="m51.1.6" xref="m51.1.6.pmml"/>                      
                  <apply id="m51.1.7.1" xref="m51.1.7.1.pmml">                   
                    <ci id="m51.1.7.1.2" xref="m51.1.7.1.2.pmml">normal-→</ci>   
                    <ci id="m51.1.7.1.1" xref="m51.1.7.1.1.pmml">n</ci>          
                    <infinity id="m51.1.7.1.3" xref="m51.1.7.1.3.pmml"/>         
                  </apply>                                                       
                </apply>                                                         
                <apply id="m51.1.15.2.2" xref="m51.1.15.2.2.pmml">               
                  <csymbol cd="ambiguous" id="m51.1.15.2.2.1">superscript</csymbol>
                  <apply id="m51.1.15.2.2.2" xref="m51.1.15.2.2.2.pmml">         
                    <csymbol cd="latexml" id="m51.1.15.2.2.2.1">norm</csymbol>   
                    <apply id="m51.1.15.2.2.2.2" xref="m51.1.15.2.2.2.2.pmml">   
                      <csymbol cd="ambiguous" id="m51.1.15.2.2.2.2.1">superscript</csymbol>
                      <ci>A</ci>                                                 
                      <ci id="m51.1.11.1" xref="m51.1.11.1.pmml">n</ci>          
                    </apply>                                                     
                  </apply>                                                       
                  <apply id="m51.1.14.1" xref="m51.1.14.1.pmml">                 
                    <divide id="m51.1.14.1.2" xref="m51.1.14.1.2.pmml"/>         
                    <cn type="integer" id="m51.1.14.1.1" xref="m51.1.14.1.1.pmml">1</cn>
                    <ci id="m51.1.14.1.3" xref="m51.1.14.1.3.pmml">n</ci>        
                  </apply>                                                       
                </apply>                                                         
              </apply>
            </apply>                                                             
                                                                                 
                                                                                 
          </math> "radius"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

 "recurrence" "relation" "Pell" "number"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

 <math>                                                                          
            <apply id="m5.1.13" xref="m5.1.13.pmml">                             
              <eq id="m5.1.6" xref="m5.1.6.pmml"/>                               
              <apply id="m5.1.13.1" xref="m5.1.13.1.pmml">                       
                <times id="m5.1.13.1.1" xref="m5.1.13.1.1.pmml"/>                
                <ci id="m5.1.1" xref="m5.1.1.pmml">I</ci>                        
                <ci id="m5.1.2" xref="m5.1.2.pmml">m</ci>                        
                <apply id="m5.1.13.1.2" xref="m5.1.13.1.2.pmml">                 
                  <csymbol cd="ambiguous" id="m5.1.13.1.2.1">subscript</csymbol> 
                  <apply id="m5.1.13.1.2.2">                                     
                    <csymbol cd="ambiguous" id="m5.1.13.1.2.2.1">superscript</csymbol>
                    <ci id="m5.1.3" xref="m5.1.3.pmml">P</ci>                    
                    <ci>+</ci>                                                   
                  </apply>                                                       
                  <ci id="m5.1.5.1" xref="m5.1.5.1.pmml">γ</ci>                  
                </apply>                                                         
              </apply>                                                           
              <apply id="m5.1.13.2" xref="m5.1.13.2.pmml">                       
                <times id="m5.1.13.2.1" xref="m5.1.13.2.1.pmml"/>                
                <apply id="m5.1.13.2.2" xref="m5.1.13.2.2.pmml">                 
                  <csymbol cd="ambiguous" id="m5.1.13.2.2.1">subscript</csymbol> 
                  <apply id="m5.1.13.2.2.2">                                     
                    <csymbol cd="ambiguous" id="m5.1.13.2.2.2.1">superscript</csymbol>
                    <ci id="m5.1.7" xref="m5.1.7.pmml">C</ci>                    
                    <ci>+</ci>                                                   
                  </apply>                                                       
                  <ci id="m5.1.9.1" xref="m5.1.9.1.pmml">μ</ci>                  
                </apply>                                                         
                <ci id="m5.1.11" xref="m5.1.11.pmml">γ</ci>                      
              </apply>                                                           
            </apply>                                                             
                                                                                 
                                                                                 
          </math> "Lisboa"
EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

 <math>                                                                          
              <mrow id="m56.1a.pmml" xref="m56.1">                               
                <mi id="m56.1.1.pmml" xref="m5.1.1">ℙ</mi>                       
                <mrow id="m56.1b.pmml" xref="m56.1">                             
                  <mo id="m56.1.2.pmml" xref="m56.1.2">[</mo>                    
                  <munder id="m56.1.15.pmml" xref="m56.1.15">                    
                    <mo movablelimits="false" id="m56.1.3.pmml" xref="m56.1.3">lim</mo>
                    <mrow id="m56.1.4.1.pmml" xref="m56.1.4.1">                  
                      <mi id="m56.1.4.1.1.pmml" xref="m56.1.4.1.1">n</mi>        
                      <mo id="m56.1.4.1.2.pmml" xref="m56.1.4.1.2">→</mo>        
                      <mi mathvariant="normal" id="m56.1.4.1.3.pmml" xref="m56.1.4.1.3">∞</mi>
                    </mrow>                                                      
                  </munder>                                                      
                  <msub id="m56.1.16.pmml" xref="m56.1.16">                      
                    <mi>A</mi>                                                   
                    <mi id="m56.1.6.1.pmml" xref="m56.1.6.1">n</mi>              
                  </msub>                                                        
                  <mo id="m56.1.7.pmml" xref="m56.1.7">=</mo>                    
                  <mi id="m56.1.8.pmml">𝐄</mi>                                   
                  <mrow id="m56.1c.pmml" xref="m56.1">                           
                    <mo id="m56.1.9.pmml" xref="m56.1.9">[</mo>                  
                    <mi>X</mi>                                                   
                    <mo id="m56.1.11.pmml" xref="m56.1.11">]</mo>                
                  </mrow>                                                        
                  <mo id="m56.1.12.pmml" xref="m56.1.12">]</mo>                  
                </mrow>                                                          
                <mo id="m56.1.13.pmml" xref="m56.1.13">=</mo>                    
                <mn id="m56.1.14.pmml" xref="m56.1.14">1</mn>                    
              </mrow>                                                            
            </math> <math>                                                       
            <cerror id="m56.1b" xref="m56.1.pmml">                               
              <csymbol cd="ambiguous" id="m56.1c" xref="m56.1.pmml">fragments</csymbol>
              <csymbol cd="unknown" id="m56.1d" xref="m56.1.pmml">P</csymbol>    
              <cerror id="m56.1e" xref="m56.1.pmml">                             
                <csymbol cd="ambiguous" id="m56.1f" xref="m56.1.pmml">fragments</csymbol>
                <ci id="m56.1.2" xref="m56.1.2.pmml">normal-[</ci>               
                <apply id="m56.1.15" xref="m56.1.15.pmml">                       
                  <csymbol cd="ambiguous" id="m56.1.15.1">subscript</csymbol>    
                  <limit id="m56.1.3" xref="m56.1.3.pmml"/>                      
                  <apply id="m56.1.4.1" xref="m56.1.4.1.pmml">                   
                    <ci id="m56.1.4.1.2" xref="m56.1.4.1.2.pmml">normal-→</ci>   
                    <ci id="m56.1.4.1.1" xref="m56.1.4.1.1.pmml">n</ci>          
                    <infinity id="m56.1.4.1.3" xref="m56.1.4.1.3.pmml"/>         
                  </apply>                                                       
                </apply>                                                         
                <apply id="m56.1.16" xref="m56.1.16.pmml">                       
                  <csymbol cd="ambiguous" id="m56.1.16.1">subscript</csymbol>    
                  <ci>A</ci>                                                     
                  <ci id="m56.1.6.1" xref="m56.1.6.1.pmml">n</ci>                
                </apply>                                                         
                <eq id="m56.1.7" xref="m56.1.7.pmml"/>                           
                <csymbol cd="unknown" id="m56.1g" xref="m56.1.pmml">E</csymbol>  
                <cerror id="m56.1h" xref="m56.1.pmml">                           
                  <csymbol cd="ambiguous" id="m56.1i" xref="m56.1.pmml">fragments</csymbol>
                  <ci id="m56.1.9" xref="m56.1.9.pmml">normal-[</ci>             
                  <csymbol cd="unknown" id="m56.1j" xref="m56.1.pmml">X</csymbol>
                  <ci id="m56.1.11" xref="m56.1.11.pmml">normal-]</ci>           
                </cerror>                                                        
                <ci id="m56.1.12" xref="m56.1.12.pmml">normal-]</ci>             
              </cerror>                                                          
              <eq id="m56.1.13" xref="m56.1.13.pmml"/>                           
              <cn type="integer" id="m56.1.14" xref="m56.1.14.pmml">1</cn>       
            </cerror>                                                            
                                                                                 
                                                                                 
          </math> "large number" "strong" "law" "number"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>                                                                          
              <mrow id="m63.1.10.pmml" xref="m63.1.10">                          
                <msub id="m63.1.10.1.pmml" xref="m63.1.10.1">                    
                  <mi id="m63.1.1.pmml" xref="m63.1.1">P</mi>                    
                  <mi id="m63.1.2.1.pmml" xref="m63.1.2.1">n</mi>                
                </msub>                                                          
                <mo id="m63.1.3.pmml" xref="m63.1.3">=</mo>                      
                <mrow id="m63.1.10.2.pmml" xref="m63.1.10.2">                    
                  <mrow id="m63.1.10.2.1.pmml" xref="m63.1.10.2.1">              
                    <mn id="m63.1.4.pmml" xref="m63.1.4">2</mn>                  
                    <mo id="m63.1.10.2.1.1.pmml" xref="m63.1.10.2.1.1">⁢</mo>    
                    <msub id="m63.1.10.2.1.2.pmml" xref="m63.1.10.2.1.2">        
                      <mi id="m63.1.5.pmml" xref="m63.1.5">P</mi>                
                      <mrow id="m63.1.6.1.pmml" xref="m63.1.6.1">                
                        <mi id="m63.1.6.1.1.pmml" xref="m63.1.6.1.1">n</mi>      
                        <mo id="m63.1.6.1.2.pmml" xref="m63.1.6.1.2">-</mo>      
                        <mn id="m63.1.6.1.3.pmml" xref="m63.1.6.1.3">1</mn>      
                      </mrow>                                                    
                    </msub>                                                      
                  </mrow>                                                        
                  <mo id="m63.1.7.pmml" xref="m63.1.7">+</mo>                    
                  <msub id="m63.1.10.2.2.pmml" xref="m63.1.10.2.2">              
                    <mi id="m63.1.8.pmml" xref="m63.1.8">P</mi>                  
                    <mrow id="m63.1.9.1.pmml" xref="m63.1.9.1">                  
                      <mi id="m63.1.9.1.1.pmml" xref="m63.1.9.1.1">n</mi>        
                      <mo id="m63.1.9.1.2.pmml" xref="m63.1.9.1.2">-</mo>        
                      <mn id="m63.1.9.1.3.pmml" xref="m63.1.9.1.3">2</mn>        
                    </mrow>                                                      
                  </msub>                                                        
                </mrow>                                                          
              </mrow>                                                            
            </math> <math>                                                       
            <apply id="m63.1.10" xref="m63.1.10.pmml">                           
              <eq id="m63.1.3" xref="m63.1.3.pmml"/>                             
              <apply id="m63.1.10.1" xref="m63.1.10.1.pmml">                     
                <csymbol cd="ambiguous" id="m63.1.10.1.1">subscript</csymbol>    
                <ci id="m63.1.1" xref="m63.1.1.pmml">P</ci>                      
                <ci id="m63.1.2.1" xref="m63.1.2.1.pmml">n</ci>                  
              </apply>                                                           
              <apply id="m63.1.10.2" xref="m63.1.10.2.pmml">                     
                <plus id="m63.1.7" xref="m63.1.7.pmml"/>                         
                <apply id="m63.1.10.2.1" xref="m63.1.10.2.1.pmml">               
                  <times id="m63.1.10.2.1.1" xref="m63.1.10.2.1.1.pmml"/>        
                  <cn type="integer" id="m63.1.4" xref="m63.1.4.pmml">2</cn>     
                  <apply id="m63.1.10.2.1.2" xref="m63.1.10.2.1.2.pmml">         
                    <csymbol cd="ambiguous" id="m63.1.10.2.1.2.1">subscript</csymbol>
                    <ci id="m63.1.5" xref="m63.1.5.pmml">P</ci>                  
                    <apply id="m63.1.6.1" xref="m63.1.6.1.pmml">                 
                      <minus id="m63.1.6.1.2" xref="m63.1.6.1.2.pmml"/>          
                      <ci id="m63.1.6.1.1" xref="m63.1.6.1.1.pmml">n</ci>        
                      <cn type="integer" id="m63.1.6.1.3" xref="m63.1.6.1.3.pmml">1</cn>
                    </apply>                                                     
                  </apply>                                                       
                </apply>                                                         
                <apply id="m63.1.10.2.2" xref="m63.1.10.2.2.pmml">               
                  <csymbol cd="ambiguous" id="m63.1.10.2.2.1">subscript</csymbol>
                  <ci id="m63.1.8" xref="m63.1.8.pmml">P</ci>                    
                  <apply id="m63.1.9.1" xref="m63.1.9.1.pmml">                   
                    <minus id="m63.1.9.1.2" xref="m63.1.9.1.2.pmml"/>            
                    <ci id="m63.1.9.1.1" xref="m63.1.9.1.1.pmml">n</ci>          
                    <cn type="integer" id="m63.1.9.1.3" xref="m63.1.9.1.3.pmml">2</cn>
                  </apply>                                                       
                </apply>                                                         
              </apply>                                                           
            </apply>                                                             
                                                                                 
                                                                                 
          </math> "recurrence relation" "Pell number" "recurrence" "relation" "Pell"

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
              <mrow id="m48.1.18.pmml" xref="m48.1.18">                          
                <mrow id="m48.1.18.1.pmml" xref="m48.1.18.1">                    
                  <munder id="m48.1.18.1.1.pmml" xref="m48.1.18.1.1">            
                    <mo movablelimits="false" id="m48.1.1.pmml" xref="m48.1.1">lim</mo>
                    <mrow id="m48.1.2.1.pmml" xref="m48.1.2.1">                  
                      <mi id="m48.1.2.1.1.pmml" xref="m48.1.2.1.1">n</mi>        
                      <mo id="m48.1.2.1.2.pmml" xref="m48.1.2.1.2">→</mo>        
                      <mi mathvariant="normal" id="m48.1.2.1.3.pmml" xref="m48.1.2.1.3">∞</mi>
                    </mrow>                                                      
                  </munder>                                                      
                  <mo id="m48.1.18.1a.pmml" xref="m48.1.18.1">⁡</mo>             
                  <mrow id="m48.1.18.1.2.pmml" xref="m48.1.18.1.2">              
                    <munder id="m48.1.18.1.2.1.pmml" xref="m48.1.18.1.2.1">      
                      <mo largeop="true" movablelimits="false" symmetric="true" id="m48.1.3.pmml" xref="m48.1.3">∫</mo>
                      <mi>X</mi>                                                 
                    </munder>                                                    
                    <mrow id="m48.1.18.1.2.2.pmml" xref="m48.1.18.1.2.2">        
                      <msub id="m48.1.18.1.2.2.2.pmml" xref="m48.1.18.1.2.2.2">  
                        <mi>f</mi>                                               
                        <mi id="m48.1.6.1.pmml" xref="m48.1.6.1">n</mi>          
                      </msub>                                                    
                      <mo id="m48.1.18.1.2.2.1.pmml" xref="m48.1.18.1.2.2.1">⁢</mo>
                      <mi id="m48.1.7.pmml" xref="m48.1.7">d</mi>                
                      <mo id="m48.1.18.1.2.2.1a.pmml" xref="m48.1.18.1.2.2.1">⁢</mo>
                      <mi>u</mi>                                                 
                    </mrow>                                                      
                  </mrow>                                                        
                </mrow>                                                          
                <mo id="m48.1.9.pmml" xref="m48.1.9">=</mo>                      
                <mrow id="m48.1.18.2.pmml" xref="m48.1.18.2">                    
                  <munder id="m48.1.18.2.1.pmml" xref="m48.1.18.2.1">            
                    <mo largeop="true" movablelimits="false" symmetric="true" id="m48.1.10.pmml" xref="m48.1.10">∫</mo>
                    <mi>X</mi>                                                   
                  </munder>                                                      
                  <mrow id="m48.1.18.2.2.pmml" xref="m48.1.18.2.2">              
                    <munder id="m48.1.18.2.2.1.pmml" xref="m48.1.18.2.2.1">      
                      <mo movablelimits="false" id="m48.1.12.pmml" xref="m48.1.12">lim</mo>
                      <mrow id="m48.1.13.1.pmml" xref="m48.1.13.1">              
                        <mi id="m48.1.13.1.1.pmml" xref="m48.1.13.1.1">n</mi>    
                        <mo id="m48.1.13.1.2.pmml" xref="m48.1.13.1.2">→</mo>    
                        <mi mathvariant="normal" id="m48.1.13.1.3.pmml" xref="m48.1.13.1.3">∞</mi>
                      </mrow>                                                    
                    </munder>                                                    
                    <mo id="m48.1.18.2.2a.pmml" xref="m48.1.18.2.2">⁡</mo>       
                    <mrow id="m48.1.18.2.2.2.pmml" xref="m48.1.18.2.2.2">        
                      <msub id="m48.1.18.2.2.2.2.pmml" xref="m48.1.18.2.2.2.2">  
                        <mi>f</mi>                                               
                        <mi id="m48.1.15.1.pmml" xref="m48.1.15.1">n</mi>        
                      </msub>                                                    
                      <mo id="m48.1.18.2.2.2.1.pmml" xref="m48.1.18.2.2.2.1">⁢</mo>
                      <mi id="m48.1.16.pmml" xref="m48.1.16">d</mi>              
                      <mo id="m48.1.18.2.2.2.1a.pmml" xref="m48.1.18.2.2.2.1">⁢</mo>
                      <mi>u</mi>                                                 
                    </mrow>                                                      
                  </mrow>                                                        
                </mrow>                                                          
              </mrow>                                                            
            </math> <math>                                                       
            <apply id="m48.1.18" xref="m48.1.18.pmml">                           
              <eq id="m48.1.9" xref="m48.1.9.pmml"/>                             
              <apply id="m48.1.18.1" xref="m48.1.18.1.pmml">                     
                <apply id="m48.1.18.1.1" xref="m48.1.18.1.1.pmml">               
                  <csymbol cd="ambiguous" id="m48.1.18.1.1.1">subscript</csymbol>
                  <limit id="m48.1.1" xref="m48.1.1.pmml"/>                      
                  <apply id="m48.1.2.1" xref="m48.1.2.1.pmml">                   
                    <ci id="m48.1.2.1.2" xref="m48.1.2.1.2.pmml">normal-→</ci>   
                    <ci id="m48.1.2.1.1" xref="m48.1.2.1.1.pmml">n</ci>          
                    <infinity id="m48.1.2.1.3" xref="m48.1.2.1.3.pmml"/>         
                  </apply>                                                       
                </apply>                                                         
                <apply id="m48.1.18.1.2" xref="m48.1.18.1.2.pmml">               
                  <apply id="m48.1.18.1.2.1" xref="m48.1.18.1.2.1.pmml">         
                    <csymbol cd="ambiguous" id="m48.1.18.1.2.1.1">subscript</csymbol>
                    <int id="m48.1.3" xref="m48.1.3.pmml"/>                      
                    <ci>X</ci>                                                   
                  </apply>                                                       
                  <apply id="m48.1.18.1.2.2" xref="m48.1.18.1.2.2.pmml">         
                    <times id="m48.1.18.1.2.2.1" xref="m48.1.18.1.2.2.1.pmml"/>  
                    <apply id="m48.1.18.1.2.2.2" xref="m48.1.18.1.2.2.2.pmml">   
                      <csymbol cd="ambiguous" id="m48.1.18.1.2.2.2.1">subscript</csymbol>
                      <ci>f</ci>                                                 
                      <ci id="m48.1.6.1" xref="m48.1.6.1.pmml">n</ci>            
                    </apply>                                                     
                    <ci id="m48.1.7" xref="m48.1.7.pmml">d</ci>                  
                    <ci>u</ci>                                                   
                  </apply>                                                       
                </apply>                                                         
              </apply>                                                           
              <apply id="m48.1.18.2" xref="m48.1.18.2.pmml">                     
                <apply id="m48.1.18.2.1" xref="m48.1.18.2.1.pmml">               
                  <csymbol cd="ambiguous" id="m48.1.18.2.1.1">subscript</csymbol>
                  <int id="m48.1.10" xref="m48.1.10.pmml"/>                      
                  <ci>X</ci>                                                     
                </apply>                                                         
                <apply id="m48.1.18.2.2" xref="m48.1.18.2.2.pmml">               
                  <apply id="m48.1.18.2.2.1" xref="m48.1.18.2.2.1.pmml">         
                    <csymbol cd="ambiguous" id="m48.1.18.2.2.1.1">subscript</csymbol>
                    <limit id="m48.1.12" xref="m48.1.12.pmml"/>                  
                    <apply id="m48.1.13.1" xref="m48.1.13.1.pmml">               
                      <ci id="m48.1.13.1.2" xref="m48.1.13.1.2.pmml">normal-→</ci>
                      <ci id="m48.1.13.1.1" xref="m48.1.13.1.1.pmml">n</ci>      
                      <infinity id="m48.1.13.1.3" xref="m48.1.13.1.3.pmml"/>     
                    </apply>                                                     
                  </apply>                                                       
                  <apply id="m48.1.18.2.2.2" xref="m48.1.18.2.2.2.pmml">         
                    <times id="m48.1.18.2.2.2.1" xref="m48.1.18.2.2.2.1.pmml"/>  
                    <apply id="m48.1.18.2.2.2.2" xref="m48.1.18.2.2.2.2.pmml">   
                      <csymbol cd="ambiguous" id="m48.1.18.2.2.2.2.1">subscript</csymbol>
                      <ci>f</ci>                                                 
                      <ci id="m48.1.15.1" xref="m48.1.15.1.pmml">n</ci>          
                    </apply>                                                     
                    <ci id="m48.1.16" xref="m48.1.16.pmml">d</ci>                
                    <ci>u</ci>                                                   
                  </apply>                                                       
                </apply>                                                         
              </apply>                                                           
            </apply>                                                             
                                                                                 
                                                                                 
          </math> "convergence"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>                                                                          
            <apply id="m13.1.8" xref="m13.1.8.pmml">                             
              <eq id="m13.1.5" xref="m13.1.5.pmml"/>                             
              <apply id="m13.1.8.1" xref="m13.1.8.1.pmml">                       
                <times id="m13.1.8.1.1" xref="m13.1.8.1.1.pmml"/>                
                <ci id="m13.1.1" xref="m13.1.1.pmml">f</ci>                      
                <ci>x</ci>                                                       
              </apply>                                                           
              <apply id="m13.1.8.2" xref="m13.1.8.2.pmml">                       
                <times id="m13.1.8.2.1" xref="m13.1.8.2.1.pmml"/>                
                <apply id="m13.1.6" xref="m13.1.6.pmml">                         
                  <divide id="m13.1.6.1"/>                                       
                  <cn type="integer" id="m13.1.6.2" xref="m13.1.6.2.pmml">1</cn> 
                  <apply id="m13.1.6.3" xref="m13.1.6.3.pmml">                   
                    <times id="m13.1.6.3.3" xref="m13.1.6.3.3.pmml"/>            
                    <ci id="m13.1.6.3.1" xref="m13.1.6.3.1.pmml">σ</ci>          
                    <apply id="m13.1.6.3.2" xref="m13.1.6.3.2.pmml">             
                      <root id="m13.1.6.3.2a" xref="m13.1.6.3.2.pmml"/>          
                      <apply id="m13.1.6.3.2.2" xref="m13.1.6.3.2.2.pmml">       
                        <times id="m13.1.6.3.2.2.3" xref="m13.1.6.3.2.2.3.pmml"/>
                        <cn type="integer" id="m13.1.6.3.2.2.1" xref="m13.1.6.3.2.2.1.pmml">2</cn>
                        <ci id="m13.1.6.3.2.2.2" xref="m13.1.6.3.2.2.2.pmml">π</ci>
                      </apply>                                                   
                    </apply>                                                     
                  </apply>                                                       
                </apply>                                                         
                <ci>z</ci>                                                       
              </apply>                                                           
            </apply>                                                             
                                                                                 
                                                                                 
          </math> "gaussian distribution" "algorithm" "gaussian"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

    <math>                                                                          
            <apply id="m11.1.8" xref="m11.1.8.pmml">                             
              <leq id="m11.1.2" xref="m11.1.2.pmml"/>                            
              <ci>x</ci>                                                         
              <apply id="m11.1.8.1" xref="m11.1.8.1.pmml">                       
                <plus id="m11.1.4" xref="m11.1.4.pmml"/>                         
                <apply id="m11.1.3" xref="m11.1.3.pmml">                         
                  <divide id="m11.1.3.1"/>                                       
                  <cn type="integer" id="m11.1.3.2" xref="m11.1.3.2.pmml">6</cn> 
                  <apply id="m11.1.3.3" xref="m11.1.3.3.pmml">                   
                    <csymbol cd="ambiguous" id="m11.1.3.3.3">superscript</csymbol>
                    <cn type="integer" id="m11.1.3.3.1" xref="m11.1.3.3.1.pmml">2</cn>
                    <ci>n</ci>                                                   
                  </apply>                                                       
                </apply>                                                         
                <apply id="m11.1.8.1.1" xref="m11.1.8.1.1.pmml">                 
                  <times id="m11.1.8.1.1.1" xref="m11.1.8.1.1.1.pmml"/>          
                  <cn type="integer" id="m11.1.6" xref="m11.1.6.pmml">12</cn>    
                  <ci id="m11.1.7" xref="m11.1.7.pmml">ϵ</ci>                    
                </apply>                                                         
              </apply>                                                           
            </apply>                                                             
                                                                                 
                                                                                 
          </math> "invariant subspace property" "property" "Banach"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

"Electron Energy" "NJL" "Energy" 

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>                                                                          
            <apply id="m50.1.10" xref="m50.1.10.pmml">                           
              <leq id="m50.1.8" xref="m50.1.8.pmml"/>                            
              <apply id="m50.1.10.1" xref="m50.1.10.1.pmml">                     
                <csymbol cd="latexml" id="m50.1.10.1.1">norm</csymbol>           
                <apply id="m50.1.10.1.2" xref="m50.1.10.1.2.pmml">               
                  <minus id="m50.1.4" xref="m50.1.4.pmml"/>                      
                  <ci>x</ci>                                                     
                  <ci>a</ci>                                                     
                </apply>                                                         
              </apply>                                                           
              <apply id="m50.1.9" xref="m50.1.9.pmml">                           
                <divide id="m50.1.9.1" xref="m5.1.9.1.pmml"/>                    
                <cn type="integer" id="m50.1.9.2" xref="m50.1.9.2.pmml">1</cn>   
                <apply id="m50.1.9.3" xref="m50.1.9.3.pmml">                     
                  <csymbol cd="latexml" id="m50.1.9.3.7">norm</csymbol>          
                  <apply id="m50.1.9.3.8" xref="m50.1.9.3.8.pmml">               
                    <csymbol cd="ambiguous" id="m50.1.9.3.8.1">superscript</csymbol>
                    <ci>a</ci>                                                   
                    <apply id="m50.1.9.3.4.1" xref="m50.1.9.3.4.1.pmml">         
                      <minus id="m50.1.9.3.4.1.1" xref="m50.1.9.3.4.1.1.pmml"/>  
                      <cn type="integer" id="m50.1.9.3.4.1.2" xref="m50.1.9.3.4.1.2.pmml">1</cn>
                    </apply>                                                     
                  </apply>                                                       
                </apply>                                                         
              </apply>                                                           
            </apply>                                                             
                                                                                 
                                                                                 
          </math> "Banach algebra" "Banach"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>                                                                          
            <cerror id="m55.1b" xref="m55.1.pmml">                               
              <csymbol cd="ambiguous" id="m55.1c" xref="m55.1.pmml">fragments</csymbol>
              <apply id="m55.1.19" xref="m55.1.19.pmml">                         
                <csymbol cd="ambiguous" id="m55.1.19.1">subscript</csymbol>      
                <limit id="m55.1.1" xref="m55.1.1.pmml"/>                        
                <apply id="m55.1.2.1" xref="m55.1.2.1.pmml">                     
                  <ci id="m55.1.2.1.2" xref="m55.1.2.1.2.pmml">normal-→</ci>     
                  <ci id="m55.1.2.1.1" xref="m55.1.2.1.1.pmml">n</ci>            
                  <infinity id="m55.1.2.1.3" xref="m55.1.2.1.3.pmml"/>           
                </apply>                                                         
              </apply>                                                           
              <csymbol cd="unknown" id="m55.1d" xref="m55.1.pmml">P</csymbol>    
              <cerror id="m55.1e" xref="m55.1.pmml">                             
                <csymbol cd="ambiguous" id="m55.1f" xref="m55.1.pmml">fragments</csymbol>
                <ci id="m55.1.4" xref="m55.1.4.pmml">normal-[</ci>               
                <ci id="m55.1.5" xref="m55.1.5.pmml">normal-|</ci>               
                <apply id="m55.1.20" xref="m55.1.20.pmml">                       
                  <csymbol cd="ambiguous" id="m55.1.20.1">subscript</csymbol>    
                  <ci>A</ci>                                                     
                  <ci id="m55.1.7.1" xref="m55.1.7.1.pmml">n</ci>                
                </apply>                                                         
                <minus id="m55.1.8" xref="m55.1.8.pmml"/>                        
                <csymbol cd="unknown" id="m55.1g" xref="m55.1.pmml">E</csymbol>  
                <cerror id="m55.1h" xref="m55.1.pmml">                           
                  <csymbol cd="ambiguous" id="m55.1i" xref="m55.1.pmml">fragments</csymbol>
                  <ci id="m55.1.10" xref="m55.1.10.pmml">normal-[</ci>           
                  <csymbol cd="unknown" id="m55.1j" xref="m55.1.pmml">X</csymbol>
                  <ci id="m55.1.12" xref="m55.1.12.pmml">normal-]</ci>           
                </cerror>                                                        
                <ci id="m55.1.13" xref="m55.1.13.pmml">normal-|</ci>             
                <gt id="m55.1.14" xref="m55.1.14.pmml"/>                         
                <csymbol cd="unknown" id="m55.1k" xref="m55.1.pmml">e</csymbol>  
                <ci id="m55.1.16" xref="m55.1.16.pmml">normal-]</ci>             
              </cerror>                                                          
              <eq id="m55.1.17" xref="m55.1.17.pmml"/>                           
              <cn type="integer" id="m55.1.18" xref="m55.1.18.pmml">0</cn>       
            </cerror>                                                            
                                                                                 
                                                                                 
          </math> "weak law" "large number" "law" "number"

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
              <mrow id="m30.1.9.pmml" xref="m30.1.9">                            
                <mrow id="m30.1.9.1.pmml" xref="m30.1.9.1">                      
                  <msup id="m30.1.9.1.1.pmml" xref="m30.1.9.1.1">                
                    <mi>x</mi>                                                   
                    <mn id="m30.1.2.1.pmml" xref="m30.1.2.1">2</mn>              
                  </msup>                                                        
                  <mo id="m30.1.3.pmml" xref="m30.1.3">-</mo>                    
                  <mi>x</mi>                                                     
                  <mo id="m30.1.3a.pmml" xref="m30.1.3">-</mo>                   
                  <mn id="m30.1.6.pmml" xref="m30.1.6">1</mn>                    
                </mrow>                                                          
                <mo id="m30.1.7.pmml" xref="m30.1.7">=</mo>                      
                <mn id="m30.1.8.pmml" xref="m30.1.8">0</mn>                      
              </mrow>                                                            
            </math> <math>                                                       
            <apply id="m30.1.9" xref="m30.1.9.pmml">                             
              <eq id="m30.1.7" xref="m30.1.7.pmml"/>                             
              <apply id="m30.1.9.1" xref="m30.1.9.1.pmml">                       
                <minus id="m30.1.3" xref="m30.1.3.pmml"/>                        
                <apply id="m30.1.9.1.1" xref="m30.1.9.1.1.pmml">                 
                  <csymbol cd="ambiguous" id="m30.1.9.1.1.1">superscript</csymbol>
                  <ci>x</ci>                                                     
                  <cn type="integer" id="m30.1.2.1" xref="m30.1.2.1.pmml">2</cn> 
                </apply>                                                         
                <ci>x</ci>                                                       
                <cn type="integer" id="m30.1.6" xref="m30.1.6.pmml">1</cn>       
              </apply>                                                           
              <cn type="integer" id="m30.1.8" xref="m30.1.8.pmml">0</cn>         
            </apply>                                                             
                                                                                 
                                                                                 
          </math> "ratio"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

<math>                                                                          
              <mrow id="m47.1.21.pmml" xref="m47.1.21">                          
                <msub id="m47.1.21.1.pmml" xref="m47.1.21.1">                    
                  <mrow id="m47.1.21.1.2.pmml" xref="m47.1.21.1.2">              
                    <mo fence="true" id="m47.1.21.1.2a.pmml" xref="m47.1.21.1.2">||</mo>
                    <mrow id="m47.1.21.1.2.2.pmml" xref="m47.1.21.1.2.2">        
                      <mi>f</mi>                                                 
                      <mo id="m47.1.21.1.2.2.1.pmml" xref="m47.1.21.1.2.2.1">⁢</mo>
                      <mi>g</mi>                                                 
                    </mrow>                                                      
                    <mo fence="true" id="m47.1.21.1.2b.pmml" xref="m47.1.21.1.2">||</mo>
                  </mrow>                                                        
                  <mn id="m47.1.7.1.pmml" xref="m47.1.7.1">1</mn>                
                </msub>                                                          
                <mo id="m47.1.8.pmml" xref="m47.1.8">≤</mo>                      
                <mrow id="m47.1.21.2.pmml" xref="m47.1.21.2">                    
                  <msub id="m47.1.21.2.2.pmml" xref="m47.1.21.2.2">              
                    <mrow id="m47.1.21.2.2.2.pmml" xref="m47.1.21.2.2.2">        
                      <mo fence="true" id="m47.1.21.2.2.2a.pmml" xref="m47.1.21.2.2.2">||</mo>
                      <mi>f</mi>                                                 
                      <mo fence="true" id="m47.1.21.2.2.2b.pmml" xref="m47.1.21.2.2.2">||</mo>
                    </mrow>                                                      
                    <mi id="m47.1.14.1.pmml" xref="m47.1.14.1">p</mi>            
                  </msub>                                                        
                  <mo id="m47.1.21.2.1.pmml" xref="m47.1.21.2.1">⁢</mo>          
                  <msub id="m47.1.21.2.3.pmml" xref="m47.1.21.2.3">              
                    <mrow id="m47.1.21.2.3.2.pmml" xref="m47.1.21.2.3.2">        
                      <mo fence="true" id="m47.1.21.2.3.2a.pmml" xref="m47.1.21.2.3.2">||</mo>
                      <mi>g</mi>                                                 
                      <mo fence="true" id="m47.1.21.2.3.2b.pmml" xref="m47.1.21.2.3.2">||</mo>
                    </mrow>                                                      
                    <mi id="m47.1.20.1.pmml" xref="m47.1.20.1">q</mi>            
                  </msub>                                                        
                </mrow>                                                          
              </mrow>                                                            
            </math> <math>                                                       
            <apply id="m47.1.21" xref="m47.1.21.pmml">                           
              <leq id="m47.1.8" xref="m47.1.8.pmml"/>                            
              <apply id="m47.1.21.1" xref="m47.1.21.1.pmml">                     
                <csymbol cd="ambiguous" id="m47.1.21.1.1">subscript</csymbol>    
                <apply id="m47.1.21.1.2" xref="m47.1.21.1.2.pmml">               
                  <csymbol cd="latexml" id="m47.1.21.1.2.1">norm</csymbol>       
                  <apply id="m47.1.21.1.2.2" xref="m47.1.21.1.2.2.pmml">         
                    <times id="m47.1.21.1.2.2.1" xref="m47.1.21.1.2.2.1.pmml"/>  
                    <ci>f</ci>                                                   
                    <ci>g</ci>                                                   
                  </apply>                                                       
                </apply>                                                         
                <cn type="integer" id="m47.1.7.1" xref="m47.1.7.1.pmml">1</cn>   
              </apply>                                                           
              <apply id="m47.1.21.2" xref="m47.1.21.2.pmml">                     
                <times id="m47.1.21.2.1" xref="m47.1.21.2.1.pmml"/>              
                <apply id="m47.1.21.2.2" xref="m47.1.21.2.2.pmml">               
                  <csymbol cd="ambiguous" id="m47.1.21.2.2.1">subscript</csymbol>
                  <apply id="m47.1.21.2.2.2" xref="m47.1.21.2.2.2.pmml">         
                    <csymbol cd="latexml" id="m47.1.21.2.2.2.1">norm</csymbol>   
                    <ci>f</ci>                                                   
                  </apply>                                                       
                  <ci id="m47.1.14.1" xref="m47.1.14.1.pmml">p</ci>              
                </apply>                                                         
                <apply id="m47.1.21.2.3" xref="m47.1.21.2.3.pmml">               
                  <csymbol cd="ambiguous" id="m47.1.21.2.3.1">subscript</csymbol>
                  <apply id="m47.1.21.2.3.2" xref="m47.1.21.2.3.2.pmml">         
                    <csymbol cd="latexml" id="m47.1.21.2.3.2.1">norm</csymbol>   
                    <ci>g</ci>                                                   
                  </apply>                                                       
                  <ci id="m47.1.20.1" xref="m47.1.20.1.pmml">q</ci>              
                </apply>                                                         
              </apply>                                                           
            </apply>                                                             
                                                                                 
                                                                                 
          </math> "Hölder"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo

query="$(cat <<EOQ

 "type" "monotone"

EOQ
)"

echo "$query" | curl --keepalive --data "limit=${limit}" --data "index=${index}" --data-urlencode 'query@-' "${wsurl}" | xmlstarlet fo
