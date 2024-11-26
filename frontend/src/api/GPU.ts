import {GPU_MODULE} from "./_prefix";
import { axios } from '../utils/request';



export interface GPUVO{
    id: number;
    name: string;
    imageUrl: string;
    price: number;
    linkUrl: string;
    type: string;
    brand: string;
}
export function newGPUVO(): GPUVO {
    return {
        id: 0, // 默认 ID
        name: '', // 默认空字符串
        imageUrl: '', // 默认空字符串
        price: 0, // 默认 0
        linkUrl: '', // 默认空字符串
        type: '', // 默认空字符串
        brand: '' // 默认空字符串
    };
}

export interface GPUVO{
    id: number;
    name: string;
    imageUrl: string;
    price: number;
    linkUrl: string;
    type: string;
    brand: string;
}
//获取所有显卡
export const getAllGPU = () => {
    return axios.get(`${GPU_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}

//根据显卡Id获取显卡
export const getGPUById = (gpuId: number) => {
    return axios.get(`${GPU_MODULE}/${gpuId}`)
        .then(res => {
            return res.data.result;
        })
}