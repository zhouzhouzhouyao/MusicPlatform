import React from "react";
import Footer from "../../../components/Footer";
import './style.css'
const MusicRadio = () => {
    return(
        <div>
           <div className='radiowrapper'>
               <div className='u-title'>
                   <h3>
                       <span className='u2-title'>
                           <a href="">音乐故事</a>
                           <span>&nbsp;·&nbsp;电台</span>
                       </span>
                       <a href="" className='more'>更多 &gt;</a>
                   </h3>
                   <ul>
                       <li>
                           <img src="http://p1.music.126.net/_99WG044O079iaJfU0H87Q==/109951165069431959.jpg?param=200y200" alt=""/>
                           <a href="" className='radio-link'>聆听(亦轩)</a>
                       </li>
                       <li>
                           <img src="http://p2.music.126.net/eAcjpv5CFAzjyJ_eRsmrqg==/109951164786498123.jpg?param=200y200" alt=""/>
                           <a href="" className='radio-link'>Yo你Yo我</a>
                       </li>
                       <li>
                           <img src="http://p1.music.126.net/_99WG044O079iaJfU0H87Q==/109951165069431959.jpg?param=200y200" alt=""/>
                           <a href="" className='radio-link'>聆听(亦轩)</a>
                       </li>
                       <li>
                           <img src="http://p2.music.126.net/eAcjpv5CFAzjyJ_eRsmrqg==/109951164786498123.jpg?param=200y200" alt=""/>
                           <a href="" className='radio-link'>Yo你Yo我</a>
                       </li>
                   </ul>
               </div>
           </div>
            <Footer/>
        </div>
    )
}

export default MusicRadio
