import React from "react";
import Footer from "../../../components/Footer";
import './style.css'

const RanList = () => {
    return(
        <div>
           <div className='rankwrapper'>
              <div className='rankleft'>
                  <h2>云音乐特色榜</h2>
                  <ul className='rank-special'>
                      <li>
                          <a>
                          <img src="http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐飙升榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>
                          
                          </a>
                      </li>
                      <li>
                          <a>
                          <img src="http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐飙升榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>
                          
                          </a>
                      </li><li>
                          <a>
                          <img src="http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐飙升榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>
                          
                          </a>
                      </li>
                  </ul>
                  <h2>全球媒体榜</h2>
                  <ul className='rank-media'>
                  <li>
                          <a>
                          <img src="http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐飙升榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>
                          
                          </a>
                      </li><li>
                          <a>
                          <img src="http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐飙升榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>
                          
                          </a>
                      </li><li>
                          <a>
                          <img src="http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=40y40" alt=""/>
                          <div className='desc'>
                            <span>云音乐飙升榜</span>
                            <p>
                                每天更新
                            </p>
                          </div>
                          
                          </a>
                      </li>
                  </ul>
              </div>
              <div className='rankright'>
                  <div className='title'>
                      <img src="http://p1.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=150y150" alt=""/>
                      <div className='desc'>
                        <h2>云音乐飙升榜</h2>
                        <div className='date'>
                            <i className='iconfont'>&#xe650;</i>
                             最近更新：07月30日 
                        </div>
                        <a href="" className='btn-first'>
                            <i className='iconfont '>&#xe645;</i>
                            播放 
                        </a>
                        <a href="">
                            <i className='iconfont '>&#xe613;</i>
                            (3220952)
                        </a>
                        <a href="">
                            <i className='iconfont '>&#xe70d;</i>
                            (8857)
                        </a>
                        <a href="">
                            <i className='iconfont '>&#xe723;</i>
                            下载
                        </a>
                        <a href="">
                        <i className='iconfont '>&#xe60c;</i>
                        (190057)
                        </a>
                      </div>
                  </div>
                  <div className='songlist'>
                      <div className='u-title'>
                          <h3>歌曲列表</h3>
                          <span>100首歌</span>
                      </div>
                      <table>
                          <thead>
                            <th></th>
                            <th>标题 </th>
                            <th>时长</th>
                            <th>歌手</th>
                          </thead>
                          <tbody>
                            <tr className='list-color-1 '>
                                <td className='first'>1</td>
                                <td className='second'>
                                    <a>
                                        <img src="http://p2.music.126.net/HvB44MNINoLar8HFbRjIGQ==/109951165142435842.jpg?param=50y50&quality=100" alt=""/>   
                                        <a class="iconfont"></a>
                                        <span>天外来物</span>
                                    </a>
                                </td>
                                <td className='third'>04:17</td>
                                <td className='fourth'>薛之谦</td>
                            </tr>
                            <tr className='list-color-2 '>
                                <td className='first'>2</td>
                                <td className='second'>
                                    <a>
                                        <img src="http://p2.music.126.net/JzsER44sOReoM6mR8XKnsw==/109951165182029540.jpg?param=50y50&quality=100" alt=""/>   
                                        <a class="iconfont"></a>
                                        <span>麻雀</span>
                                    </a>
                                </td>
                                <td className='third'>04:12</td>
                                <td className='fourth'>李荣浩</td>
                            </tr>
                          </tbody>
                      </table>
                  </div>
              </div>
           </div>
          <Footer/>
        </div>
    )
}

export default RanList
