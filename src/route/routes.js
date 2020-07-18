import Home from "../page/Home";
import MyMusic from "../page/MyMusic";
import Friend from "../page/Friend";
import Shop from "../page/Shop";
import Musician from "../page/Musician";
import Download from "../page/Download";
import NotFound from "../page/NotFound";


const routes = [
    {
        path: '/home',
        component: Home,
    },
    {
        path: '/mymusic',
        component: MyMusic,
        exact: true
    },
    {
        path: '/friend',
        component: Friend,
        exact: true
    },
    {
        path: '/shop',
        component: Shop,
        exact: true
    },
    {
        path: '/musician',
        component: Musician,
        exact: true
    },
    {
        path: '/download',
        component: Download,
        exact: true
    },
    {
        component: NotFound,
    }
]

export default routes;
