import React from "react";
import { Carousel, Row, Col, BackTop } from 'antd';
import { LeftOutlined, RightOutlined, ChromeOutlined } from '@ant-design/icons';
import './style.css'

class Home extends React.Component {
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
        }
        return(
            <div>
                <LeftOutlined className="left-out-line"  onClick={ this.prev }/>
                <Carousel {...lunboSetting} ref={el => (this.slider = el)}>
                    {/* 此处存放图片 */}
                    <div>
                        <h3>1</h3>
                    </div>
                    <div>
                        <h3>2</h3>
                    </div>
                    <div>
                        <h3>3</h3>
                    </div>
                    <div>
                        <h3>4</h3>
                    </div>
                </Carousel>
                <RightOutlined className="right-out-line" onClick={ this.next }/>

                <Row className="main-music">
                    <Col span={11} offset={4} className="left-music">
                        col-8
                        <br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/><br/>
                    </Col>
                    <Col span={5} className="right-music">
                        col-8
                    </Col>
                    <Col span={1} className="back-to-top">
                        {/* 回到顶部 */}
                        <BackTop />
                    </Col>
                </Row>

                <Row className="footer">
                    <Col span={15} offset={5}>
                        <div className="copy-right">
                            <p className="service-link">
                                <a href="#">服务条款</a>
                                <span>  |  </span>
                                <a href="#">隐私政策</a>
                                <span>  |  </span>
                                <a href="#">儿童隐私政策</a>
                                <span>  |  </span>
                                <a href="#">版权投诉指引</a>
                                <span>  |  </span>
                                <a href="#">意见反馈</a>
                            </p>
                            <p>云音乐平台版权所有©2020-2020</p>
                            <p>违法和不良信息举报电话：21345</p>
                            <p>测试性实习网站</p>
                        </div>
                        <div className="admire">
                            <span>
                                <ChromeOutlined />
                                <em>用户中心</em>
                            </span>
                            <span>
                                <ChromeOutlined />
                                <em>赞赏</em>
                            </span>
                            <span>
                                <ChromeOutlined />
                                <em>独立音乐人</em>
                            </span>
                        </div>
                    </Col>
                </Row>
            </div>
        )
    }
}


export default Home
