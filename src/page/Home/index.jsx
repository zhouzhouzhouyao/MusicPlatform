import React, { component } from "react";
import { Carousel } from 'antd';
import { LeftOutlined, RightOutlined } from '@ant-design/icons';
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
            </div>
        )
    }
}


export default Home
