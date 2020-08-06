import React,{Component} from "react";
import Footer from "../../../components/Footer";
import './style.css'
import { Menu, Dropdown } from 'antd';
import { DownOutlined } from '@ant-design/icons';

const menu = (
  <div className='songlist-style'>
            <dt>
                <i className='iconfont'>&#xe6c8;</i>
                <span>风格</span>
            </dt>
            {/* 下拉菜单 */}
            <dd> 
                <a href='javascript:;'>流行</a>| 
                <a href='javascript:;'>摇滚</a>|
                <a href='javascript:;'>民谣</a>|
                <a href='javascript:;'>电子</a>|
                <a href='javascript:;'>舞曲</a>|
                <a href='javascript:;'>说唱</a>|
                <a href='javascript:;'> 轻音乐</a>|
                <a href='javascript:;'> 爵士</a>|
                <a href='javascript:;'> 乡村</a>|
                <a href='javascript:;'> 古典</a>|
                <a href='javascript:;'> 乡村</a>|
                <a href='javascript:;'> 民族</a>|
                <a href='javascript:;'> 英伦</a>|
                <a href='javascript:;'> 金属</a>|
                <a href='javascript:;'> 朋克</a>|
                <a href='javascript:;'> 蓝调</a>|
                <a href='javascript:;'>世界音乐</a>|
                <a href='javascript:;'>古风</a>|
                <a href='javascript:;'>后摇</a>|
            </dd>
         </div>

)


class SongList extends Component {
    constructor(props){
     super(props)
     this.state = {
         isShow:false
     }
    //  this.getStyle = this.getStyle.bind(this)
    //  this.show = this.show.bind(this)
    //  this.hide=this.hide.bind(this)
    }
    getStyle(){
        return(
            <Dropdown overlay={menu}>
            <a className="ant-dropdown-link select-sort" onClick={e => e.preventDefault()}>
              选择分类 <DownOutlined />
            </a>
          </Dropdown>

        
        )
    }

    render(){
    return(
            <div className='hot-recommend all-songlist'>
                          <div className='songlist-title'>
                              <span>全部</span>         
                              {
                                  this.getStyle()
                              }
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
            <Footer/> 
         </div>
      
    )
} }

export default SongList
