import {CPU_MODULE} from "./_prefix";
import { axios } from '../utils/request';


export interface CPUVO {
    id: number; // 唯一标识符
    name: string; // CPU名称
    imageUrl: string; // 图片链接
    price: number; // 价格
    linkUrl: string; // 购买链接
    type: string; // CPU 类型，和主板的 type 对应
}
// 初始化 CPUVO
export function newCPUVO(): CPUVO {
    return {
        id: 0, // 默认 ID
        name: '', // 默认空字符串
        imageUrl: '', // 默认空字符串
        price: 0, // 默认 0
        linkUrl: '', // 默认空字符串
        type: '' // 默认空字符串
    };
}

//获取所有CPU
export const getAllCPU = () => {
    return axios.get(`${CPU_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}

//根据CPUId获取CPU
export const getCPUById = (cpuId: number) => {
    return axios.get(`${CPU_MODULE}/${cpuId}`)
        .then(res => {
            return res.data.result;
        })
}