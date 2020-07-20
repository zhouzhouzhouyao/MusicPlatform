import React from "react";
import { Carousel, Row, Col, BackTop } from 'antd';
import { LeftOutlined, RightOutlined, ChromeOutlined } from '@ant-design/icons';
import './style.css'
import Footer from "../../../components/Footer";
import pinkfloyd from '../../../../src/assets/MusicCover/Pink-Floyd.jpg'

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

                {/* 页脚 */}
                <Footer/>
            </div>
        )
    }
}

export default Recommend
