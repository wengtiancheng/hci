import AllSolutions from "../views/AllSolutions.vue";
import SolutionDetail from "../views/SolutionDetail.vue";
import {createRouter, createWebHistory} from "vue-router";
import SelectComponent from "../views/SelectComponent.vue";
import selfService from "../views/selfService.vue";


const routes = [
    {
        path: '/solution',
        name: 'AllSolutions',
        component: AllSolutions
    },{
        path: '/select/:type',
        component: SelectComponent,
        meta: { title: '选择配件', showHeader: true }
    },{
        path: '/selfService',
        component: selfService,
        meta: { title: '自助装机', showHeader: true }
    },{
        path: '/',
        redirect: '/login'
    },{
        path: '/login',
        component: () => import('../views/Login.vue'),
        meta: { title: '登录', showHeader: false }
    }, {
        path: '/register',
        component: () => import('../views/Register.vue'),
        meta: { title: '注册', showHeader: false }

    },{
        path: '/solution/:id',
        name: 'SolutionDetail',
        component: SolutionDetail
    },{
        path: '/select/:type',
        component: SelectComponent,
        meta: { title: '选择配件', showHeader: true }

    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
})

export default router

