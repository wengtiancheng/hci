import {HARD_DISK_MODULE} from "./_prefix";
import { axios } from '../utils/request';


export interface HardDiskVO {
    id: number;
    name: string;
    imageUrl: string;
    price: number;
    capacity: number;
    linkUrl: string;
    type: string;
    brand: string;
}

export function newHardDiskVO(): HardDiskVO {
    return {
        id: 0,
        name: '',
        imageUrl: '',
        price: 0,
        capacity: 0,
        linkUrl: '',
        type: '',
        brand: ''
    }
}


//获取所有硬盘
export const getAllHarddisk = () => {
    return axios.get(`${HARD_DISK_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}

//根据硬盘Id获取硬盘
export const getHarddiskById = (harddiskId: number) => {
    return axios.get(`${HARD_DISK_MODULE}/${harddiskId}`)
        .then(res => {
            return res.data.result;
        })
}