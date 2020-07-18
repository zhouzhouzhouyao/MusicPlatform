import React from "react";
import './style.css'
import { Row, Col, Input, Button } from 'antd';
import { HashRouter as Router, Switch, Route, NavLink, Redirect} from "react-router-dom";

const DiscoverNav = () =>{
    return(
        <Router>
            <Row>
                <Col span={14} offset={4}>
                    <ul>
                        <li>
                            <NavLink>推荐</NavLink>
                        </li>
                        <li>
                            <NavLink>排行榜</NavLink>
                        </li>
                        <li>
                            <NavLink>歌单</NavLink>
                        </li>
                        <li>
                            <NavLink>主播电台</NavLink>
                        </li>
                        <li>
                            <NavLink>歌手</NavLink>
                        </li>
                        <li>
                            <NavLink>新碟上架</NavLink>
                        </li>
                    </ul>
                </Col>
            </Row>
        </Router>
    )
}
