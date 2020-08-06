import React from "react";
import { Carousel, Row, Col, BackTop } from 'antd';
import { LeftOutlined, RightOutlined, ChromeOutlined } from '@ant-design/icons';
import './style.css'
import Footer from "../../../components/Footer";
import pinkfloyd from '../../../../src/assets/MusicCover/Pink-Floyd.jpg'

import {icon1,icon2} from '../../../statics/yuandian.png'
import '../../../statics/iconfont/iconfont.css'

class Recommend extends React.Component{
    constructor(props) {
        super(props);
        this.next = this.next.bind(this);
        this.prev = this.prev.bind(this);
    }
    next() {
        this.slider.slick.slickNext();
    }
    prev() {
        this.slider.slick.slickPrev();
    }

    render() {
        const lunboSetting = {
            dots: true,
            autoplay:true,
        };
        return(
            <div>
                <LeftOutlined className="left-out-line"  onClick={ this.prev }/>
                <Carousel {...lunboSetting} ref={el => (this.slider = el)}>
                    {/* 此处存放图片 */}
                    <div>
                        <img src={pinkfloyd} alt="PINK-FLOYD"/>
                    </div>
                    <div>
                        <img src={pinkfloyd} alt="PINK-FLOYD"/>
                    </div>
                    <div>
                        <img src={pinkfloyd} alt="PINK-FLOYD"/>
                    </div>
                    <div>
                        <img src={pinkfloyd} alt="PINK-FLOYD"/>
                    </div>
                </Carousel>
                <RightOutlined className="right-out-line" onClick={ this.next }/>

                <Row className="main-music">
                    <Col span={12} offset={4} className="left-music">
                          {/* 热门推荐 */}
                        <div className='hot-recommend'>
                          <div className='title'>
                            <i className='iconfont'>&#xe7c2;</i>
                              热门推荐
                          </div>
                          <ul className='recommend-list'>
                              <li>
                                  <div className='bottom'>
                                      <i className='iconfont'>&#xe618;</i>
                                      <span>110万 </span>
                                      <a className='iconfont'>&#xe645;</a>
                                  </div>
                                  <a className='desc'>
                                     希望这个夏天，西瓜甜一点，快乐多一点
                                  </a>
                              </li>
                              <li>
                                  <div className='bottom'>
                                      <i className='iconfont'>&#xe618;</i>
                                      <span>110万 </span>
                                      <a className='iconfont'>&#xe645;</a>
                                  </div>
                                  <a className='desc'>
                                     希望这个夏天，西瓜甜一点，快乐多一点
                                  </a>
                              </li>
                              <li>
                                  <div className='bottom'>
                                      <i className='iconfont'>&#xe618;</i>
                                      <span>110万 </span>
                                      <a className='iconfont'>&#xe645;</a>
                                  </div>
                                  <a className='desc'>
                                     希望这个夏天，西瓜甜一点，快乐多一点
                                  </a>
                              </li>
                              <li>
                                  <div className='bottom'>
                                      <i className='iconfont'>&#xe618;</i>
                                      <span>110万 </span>
                                      <a className='iconfont'>&#xe645;</a>
                                  </div>
                                  <a className='desc'>
                                     希望这个夏天，西瓜甜一点，快乐多一点
                                  </a>
                              </li>
                              <li>
                                  <div className='bottom'>
                                      <i className='iconfont'>&#xe618;</i>
                                      <span>110万 </span>
                                      <a className='iconfont'>&#xe645;</a>
                                  </div>
                                  <a className='desc'>
                                     希望这个夏天，西瓜甜一点，快乐多一点
                                  </a>
                              </li>
                              <li>
                                  <div className='bottom'>
                                      <i className='iconfont'>&#xe618;</i>
                                      <span>110万 </span>
                                      <a className='iconfont'>&#xe645;</a>
                                  </div>
                                  <a className='desc'>
                                     希望这个夏天，西瓜甜一点，快乐多一点
                                  </a>
                              </li>
                              <li>
                                  <div className='bottom'>
                                      <i className='iconfont'>&#xe618;</i>
                                      <span>110万 </span>
                                      <a className='iconfont'>&#xe645;</a>
                                  </div>
                                  <a className='desc'>
                                     希望这个夏天，西瓜甜一点，快乐多一点
                                  </a>
                              </li>
                          </ul>
                        </div>
                          {/* 榜单 */}
                        <div className='hot-list'>
                          <div className='title'>
                            <i className='iconfont'>&#xe7c2;</i>
                              榜单
                          </div>
                          <dl>
                              <dt className='top list-color-1'>
                                  <img src="http://p3.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=100y100" alt=""/>
                                  <div className='desc'>
                                    <h4>云音乐飙升榜</h4>
                                    <a className='iconfont'>&#xe645;</a>
                                    <a className='iconfont'>&#xe613;</a>
                                  </div>
                              </dt>
                              <dd>
                                  <ol>
                                      <li className='list-item list-color-2'>
                                          <span>1</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>2</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>3</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>4</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>5</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>6</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>7</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>8</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>9</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>10</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>10</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                  </ol>
                              </dd>

                          </dl>
                          <dl>
                              <dt className='top list-color-1'>
                                  <img src="http://p3.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=100y100" alt=""/>
                                  <div className='desc'>
                                    <h4>云音乐飙升榜</h4>
                                    <a className='iconfont'>&#xe645;</a>
                                    <a className='iconfont'>&#xe613;</a>
                                  </div>
                              </dt>
                              <dd>
                                  <ol>
                                      <li className='list-item list-color-2'>
                                          <span>1</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>2</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>3</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>4</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>5</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>6</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>7</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>8</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>9</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>10</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>10</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                  </ol>
                              </dd>

                          </dl>
                          <dl>
                              <dt className='top list-color-1'>
                                  <img src="http://p3.music.126.net/DrRIg6CrgDfVLEph9SNh7w==/18696095720518497.jpg?param=100y100" alt=""/>
                                  <div className='desc'>
                                    <h4>云音乐飙升榜</h4>
                                    <a className='iconfont'>&#xe645;</a>
                                    <a className='iconfont'>&#xe613;</a>
                                  </div>
                              </dt>
                              <dd>
                                  <ol>
                                      <li className='list-item list-color-2'>
                                          <span>1</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>2</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>3</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>4</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>5</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>6</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>7</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>8</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>9</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-1'>
                                          <span>10</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                      <li className='list-item list-color-2'>
                                          <span>10</span>
                                          <a href=''>夏天的风</a>
                                      </li>
                                  </ol>
                              </dd>

                          </dl>
                        </div>
                    </Col>
                    <Col span={5} className="right-music">
                       {/* 歌手 */}
                        <div className='singer'>
                           <h3>
                              <span>入驻歌手</span>
                              <a href=''>查看全部&nbsp;&gt;</a>
                           </h3>
                           <ul>
                              <li>
                                  <a><img src='http://p2.music.126.net/p9U80ex1B1ciPFa125xV5A==/5931865232210340.jpg?param=62y62'/>
                                    <div className='info'>
                                        <h4>张惠妹aMEI</h4>
                                        <p>台湾歌手张惠妹</p>
                                    </div>
                                  </a>
                              </li>
                              <li>
                                  <a><img src='http://p2.music.126.net/p9U80ex1B1ciPFa125xV5A==/5931865232210340.jpg?param=62y62'/>
                                    <div className='info'>
                                        <h4>张惠妹aMEI</h4>
                                        <p>台湾歌手张惠妹</p>
                                    </div>
                                  </a>
                              </li>
                              <li>
                                  <a><img src='http://p2.music.126.net/p9U80ex1B1ciPFa125xV5A==/5931865232210340.jpg?param=62y62'/>
                                    <div className='info'>
                                        <h4>张惠妹aMEI</h4>
                                        <p>台湾歌手张惠妹</p>
                                    </div>
                                  </a>
                              </li>
                              <li>
                                  <a><img src='http://p2.music.126.net/p9U80ex1B1ciPFa125xV5A==/5931865232210340.jpg?param=62y62'/>
                                    <div className='info'>
                                        <h4>张惠妹aMEI</h4>
                                        <p>台湾歌手张惠妹</p>
                                    </div>
                                  </a>
                              </li>
                              <li>
                                  <a><img src='http://p2.music.126.net/p9U80ex1B1ciPFa125xV5A==/5931865232210340.jpg?param=62y62'/>
                                    <div className='info'>
                                        <h4>张惠妹aMEI</h4>
                                        <p>台湾歌手张惠妹</p>
                                    </div>
                                  </a>
                              </li>
                           </ul>
                           <a href='' className='apply'>申请成为音乐人</a>
                        </div>
                    </Col>
                    <Col span={1} className="back-to-top">
                        {/* 回到顶部 */}
                        <BackTop />
                    </Col>
                </Row>
                {/* 页脚 */}
                <Footer/>
            </div>
        )
    }
}

export default Recommend
