import {MEMORY_MODULE} from "./_prefix";
import { axios } from '../utils/request';



export interface MemoryVO {
    id: number;
    name: string;
    imageUrl: string;
    price: number;
    linkUrl: string;
    type: string;
    brand: string;
}
export function newMemoryVO(): MemoryVO {
    return {
        id: 0,
        name: '',
        imageUrl: '',
        price: 0,
        linkUrl: '',
        type: '',
        brand: ''
    }
}

//获取所有内存
export const getAllMemory = () => {
    return axios.get(`${MEMORY_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}

//根据内存Id获取内存
export const getMemoryById = (memoryId: number) => {
    return axios.get(`${MEMORY_MODULE}/${memoryId}`)
        .then(res => {
            return res.data.result;
        })
}