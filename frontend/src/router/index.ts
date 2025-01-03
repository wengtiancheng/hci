import { createRouter, createWebHistory, RouteRecordRaw } from 'vue-router';
import AllSolutions from '../views/AllSolutions.vue';
import SolutionDetail from '../views/SolutionDetail.vue';
import SelfService from '../views/SelfService.vue';
import SelectChassis from '../views/SelectPages/SelectChassis.vue';
import SelectCPU from '../views/SelectPages/SelectCPU.vue';
import SelectGPU from '../views/SelectPages/SelectGPU.vue';
import SelectMemory from '../views/SelectPages/SelectMemory.vue';
import SelectMotherboard from '../views/SelectPages/SelectMotherboard.vue';
import SelectPowersupply from '../views/SelectPages/SelectPowersupply.vue';
import SelectCooling from '../views/SelectPages/SelectCooling.vue';
import SelectHarddisk from '../views/SelectPages/SelectHarddisk.vue';
import SelectDisplay from '../views/SelectPages/SelectDisplay.vue';
import HandleNeed from '../views/HandleNeed.vue';
import MySolutions from '../views/MySolutions.vue';

const routes: Array<RouteRecordRaw> = [
    {
        path: '/',
        redirect: '/need'
    },
    {
        path: '/solution',
        component: AllSolutions,
        meta: { title: '所有装机方案', showHeader: true }
    },
    {
        path: '/custom-build',
        component: SelfService,
        meta: { title: '自助装机', showHeader: true }
    },
    {
        path: '/mySolutions',
        component: MySolutions,
        meta: { title: '我的装机', showHeader: true }
    },
    {
        path: '/login',
        component: () => import('../views/Login.vue'),
        meta: { title: '登录', showHeader: false }
    },
    {
        path: '/register',
        component: () => import('../views/Register.vue'),
        meta: { title: '注册', showHeader: false }
    },
    {
        path: '/need',
        component: HandleNeed,
        meta: { title: '处理需求', showHeader: true }
    },
    {
        path: '/solution/:id',
        component: SolutionDetail,
        meta: { title: '查看方案', showHeader: true }
    },
    {
        path: '/select/chassis',
        component: SelectChassis,
        meta: { title: '选择机箱', showHeader: true }
    },
    {
        path: '/select/cpu',
        component: SelectCPU,
        meta: { title: '选择CPU', showHeader: true }
    },
    {
        path: '/select/gpu',
        component: SelectGPU,
        meta: { title: '选择显卡', showHeader: true }
    },
    {
        path: '/select/memory',
        component: SelectMemory,
        meta: { title: '选择内存', showHeader: true }
    },
    {
        path: '/select/motherboard',
        component: SelectMotherboard,
        meta: { title: '选择主板', showHeader: true }
    },
    {
        path: '/select/powersupply',
        component: SelectPowersupply,
        meta: { title: '选择电源', showHeader: true }
    },
    {
        path: '/select/cooling',
        component: SelectCooling,
        meta: { title: '选择散热器', showHeader: true }
    },
    {
        path: '/select/harddisk',
        component: SelectHarddisk,
        meta: { title: '选择存储设备', showHeader: true }
    },
    {
        path: '/select/display',
        component: SelectDisplay,
        meta: { title: '选择显示器', showHeader: true }
    }
];

const router = createRouter({
    history: createWebHistory(import.meta.env.BASE_URL),
    routes,
});

export default router;