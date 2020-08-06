import React from "react";
import Footer from "../../../components/Footer";
import './style.css'
const Songer = () => {
    return(
        <div>
        <div className='all-songlist'>
        <div className='songlist-title'>
            <span>入驻歌手</span>
            <a className='more'>更多 &gt;</a>
        </div>
        {/* 歌手列表 */}
        <ul className='songer-list'>
            <li>
                <img src="http://p1.music.126.net/F9asgcj7C7qSl_je9XDvRw==/603631883675241.jpg?param=130y130" alt=""/>
                <a className='desc'>
                  张惠妹
                </a>
                <i className='iconfont'>&#xe62e;</i>
                
            </li>
            <li>
                <img src="http://p1.music.126.net/F9asgcj7C7qSl_je9XDvRw==/603631883675241.jpg?param=130y130" alt=""/>
                <a className='desc'>
                  张惠妹
                </a>
                <i className='iconfont'>&#xe62e;</i>
                
            </li>  <li>
                <img src="http://p1.music.126.net/F9asgcj7C7qSl_je9XDvRw==/603631883675241.jpg?param=130y130" alt=""/>
                <a className='desc'>
                  张惠妹
                </a>
                <i className='iconfont'>&#xe62e;</i>
                
            </li>  <li>
                <img src="http://p1.music.126.net/F9asgcj7C7qSl_je9XDvRw==/603631883675241.jpg?param=130y130" alt=""/>
                <a className='desc'>
                  张惠妹
                </a>
                <i className='iconfont'>&#xe62e;</i>
                
            </li>  <li>
                <img src="http://p1.music.126.net/F9asgcj7C7qSl_je9XDvRw==/603631883675241.jpg?param=130y130" alt=""/>
                <a className='desc'>
                  张惠妹
                </a>
                <i className='iconfont'>&#xe62e;</i>
                
            </li>
           
        </ul>
   
</div>
<Footer/> 
</div>
    )
}

export default Songer
