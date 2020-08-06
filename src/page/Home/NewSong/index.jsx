import React from "react";
import Footer from "../../../components/Footer";

const NewSong = () => {
    return(
        <div>
        <div className='all-songlist'>
        <div className='songlist-title'>
            <span>热门新碟</span> 
        </div>
        {/* 歌单列表 */}
        <ul className='songer-list'>
            <li>
                <img src="http://p3.music.126.net/JzsER44sOReoM6mR8XKnsw==/109951165182029540.jpg?param=130y130" alt=""/>
                <a className='desc'>
                  麻雀
                </a>
                <div className='singer'>李荣浩</div>
            </li>
            <li>
                <img src="http://p3.music.126.net/JzsER44sOReoM6mR8XKnsw==/109951165182029540.jpg?param=130y130" alt=""/>
                <a className='desc'>
                  麻雀
                </a>
                <div className='singer'>李荣浩</div>
            </li><li>
                <img src="http://p3.music.126.net/JzsER44sOReoM6mR8XKnsw==/109951165182029540.jpg?param=130y130" alt=""/>
                <a className='desc'>
                  麻雀
                </a>
                <div className='singer'>李荣浩</div>
            </li>
            <li>
                <img src="http://p3.music.126.net/JzsER44sOReoM6mR8XKnsw==/109951165182029540.jpg?param=130y130" alt=""/>
                <a className='desc'>
                  麻雀
                </a>
                <div className='singer'>李荣浩</div>
            </li>
        </ul>
    </div>
   <Footer/> 
</div>
    )
}

export default NewSong
