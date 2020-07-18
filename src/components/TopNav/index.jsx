import React from 'react'
import './style.css'
import { Row, Col, Input } from 'antd';
import { BrowserRouter as Router, Switch, Route, NavLink, Redirect} from "react-router-dom";
import { renderRoutes } from 'react-router-config'
import routes from "../../route/routes";

const { Search } = Input;

const TopNav = () =>{
    return(
        <Router>
            <Row align="middle" className="top-nav">
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
            {/* 加载路由组件 */}
            <Switch>
                <Redirect exact path="/" to="home" />
                { renderRoutes(routes) }
            </Switch>
        </Router>
    )
}

export default TopNav
