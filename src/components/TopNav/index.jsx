import React from 'react'
import './style.css'
import { Row, Col, Input, Button } from 'antd';
import { HashRouter as Router, Switch, Route, NavLink, Redirect} from "react-router-dom";
import Home from "../../page/Home";
import Discover from "../../page/Discover";
import MyMusic from "../../page/MyMusic";
import Friend from "../../page/Friend";
import Shop from "../../page/Shop";
import Musician from "../../page/Musician";
import Download from "../../page/Download";
import NotFound from "../../page/NotFound";

const { Search } = Input;

const TopNav = () =>{
    return(
        <Router>
            <Row align="middle">
                <Col span={16} offset={4} className="left-nav">
                    <ul>
                        <li>
                            <NavLink to="/home">发现音乐</NavLink>
                        </li>
                        <li>
                            <NavLink to="/mymusic">我的音乐</NavLink>
                        </li>
                        <li>
                            <NavLink to="/friend">朋友</NavLink>
                        </li>
                        <li>
                            <NavLink to="/shop">商城</NavLink>
                        </li>
                        <li>
                            <NavLink to="/musician">音乐人</NavLink>
                        </li>
                        <li>
                            <NavLink to="/download">下载客户端</NavLink>
                        </li>
                    </ul>
                </Col>

                <Col span={8} offset={15}>
                    <Search
                        className="search"
                        placeholder="搜索音乐/用户"
                        onSearch={value => console.log(value)}
                        style={{ width: 150 }}
                    />
                    <span className="login">
                        登陆
                    </span>
                </Col>
            </Row>

            <Switch>
                {/* 重定向主页，直接跳转到home页面 */}
                <Redirect exact path="/" to="home" />
                <Route  path="/home" component={ Home }/>
                {/*<Route exact path="/discover" component={ Discover }/>*/}
                <Route exact path="/mymusic" component={ MyMusic }/>
                <Route exact path="/friend" component={ Friend }/>
                <Route exact path="/shop" component={ Shop }/>
                <Route exact path="/musician" component={ Musician }/>
                <Route exact path="/download" component={ Download }/>
                <Route component={ NotFound }/>
            </Switch>
        </Router>
    )
}

export default TopNav
