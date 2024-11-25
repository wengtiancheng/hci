import {createRouter, createWebHashHistory} from 'vue-router';



const router = createRouter({
    //设置路由的模式
    history:createWebHashHistory(),
    routes: [{
        path: '/',
        redirect: '/login',
    },{
        path: '/login',
        component: () => import('../views/Login.vue'),
        meta: {title: '用户登录', showHeader: false}
    },{
        path: '/register',
        component: () => import('../views/Register.vue'),
        meta: {title: '用户注册', showHeader: false}
    },{
        path: '/self_service',
        component: () => import('../views/selfService.vue'),
        meta: {title: '自助装机', showHeader: true}
    },{
        path:'/all_solutions',
        component: () => import('../components/AllSolutions.vue'),
        meta: {title: '装机广场', showHeader: true}
    }]
})




export {router}