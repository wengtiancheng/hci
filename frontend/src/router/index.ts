import AllSolutions from "../views/AllSolutions.vue";
import SolutionDetail from "../views/SolutionDetail.vue";
import {createRouter, createWebHistory} from "vue-router";
import selfService from "../views/selfService.vue";
import SelectChassis from "../views/SelectPages/SelectChassis.vue";
import SelectCPU from "../views/SelectPages/SelectCPU.vue";
import SelectGPU from "../views/SelectPages/SelectGPU.vue";
import SelectMemory from "../views/SelectPages/SelectMemory.vue";
import SelectMotherboard from "../views/SelectPages/SelectMotherboard.vue";
import SelectPowersupply from "../views/SelectPages/SelectPowersupply.vue";
import SelectCooling from "../views/SelectPages/SelectCooling.vue";
import SelectHarddisk from "../views/SelectPages/SelectHarddisk.vue";
import SelectDisplay from "../views/SelectPages/SelectDisplay.vue";



const routes = [
    {
        path: '/solution',
        name: 'AllSolutions',
        component: AllSolutions
    },{
        path: '/custom-build',
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
        path: '/select/chassis',
        component: SelectChassis,
        meta: { title: '选择机箱', showHeader: true }
    },{
        path: '/select/cpu',
        component: SelectCPU,
        meta: { title: '选择CPU', showHeader: true }
    },{
        path: '/select/gpu',
        component: SelectGPU,
        meta: { title: '选择显卡', showHeader: true }
    },{
        path: '/select/memory',
        component: SelectMemory,
        meta: { title: '选择内存', showHeader: true }
    },{
        path: '/select/motherboard',
        component: SelectMotherboard,
        meta: { title: '选择主板', showHeader: true }
    },{
        path: '/select/powersupply',
        component: SelectPowersupply,
        meta: { title: '选择电源', showHeader: true }
    },{
        path: '/select/cooling',
        component: SelectCooling,
        meta: { title: '选择散热器', showHeader: true }
    },{
        path: '/select/storage',
        component: SelectHarddisk,
        meta: { title: '选择存储设备', showHeader: true }
    },{
        path: '/select/display',
        component: SelectDisplay,
        meta: { title: '选择显示器', showHeader: true }
    }
]

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
})

export default router

