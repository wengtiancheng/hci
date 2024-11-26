import {SOLUTION_MODULE} from "./_prefix";

import { axios } from '../utils/request';

export interface SolutionVO {
    id: number;
    name: string;
    imageUrl: string;
    totalPrice: number;
    description: string;
    saveNum: number;
    createTime: Date;
    cpuId: number;
    motherboardId: number;
    gpuId: number;
    memoryId: number;
    harddiskId: number;
    powersupplyId: number;
    coolingId: number;
    chassisId: number;
    displayId: number;
}

// 初始化 SolutionVO
export function newSolutionVO(): SolutionVO {
    return {
        id: 0, // 默认 ID
        name: '', // 默认空字符串
        imageUrl: '', // 默认空字符串
        totalPrice: 0, // 默认 0
        description: '', // 默认空字符串
        saveNum: 0, // 默认 0
        createTime: new Date(), // 当前时间
        cpuId: 0, // 默认 CPU ID
        motherboardId: 0, // 默认主板 ID
        gpuId: 0, // 默认 GPU ID
        memoryId: 0, // 默认内存 ID
        harddiskId: 0, // 默认硬盘 ID
        powersupplyId: 0, // 默认电源 ID
        coolingId: 0, // 默认散热 ID
        chassisId: 0, // 默认机箱 ID
        displayId: 0 // 默认显示器 ID
    };
}

//获取所有解决方案
export const getAllSolutionNofilter = () => {
    return axios.get(`${SOLUTION_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}
// 获取所有解决方案（可以添加筛选参数）
export const getAllSolution = (filters: any) => {
    return axios.get(`${SOLUTION_MODULE}/all`, { params: filters })
        .then(res => {
            return res.data.result;  // 假设返回的数据是数组
        });
};


//上传一个装机方案
export const uploadSolution = (solution: SolutionVO) => {
    return axios.post(`${SOLUTION_MODULE}/save`, solution)
        .then(res => {
            return res
        })
}

// 收藏一个装机方案
export const starSolution = (id: number) => {
    return axios.post(`${SOLUTION_MODULE}/star/${id}`)
        .then(res => {
            return res.data.result;
        })
}

// 获取一个装机方案
export const getSolution = (id: number) => {
    return axios.get(`${SOLUTION_MODULE}/${id}`)
        .then(res => {
            return res.data.result;
        })
}
