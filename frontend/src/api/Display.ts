import {DISPLAY_MODULE} from "./_prefix";
import { axios } from '../utils/request';


export interface DisplayVO {
    id: number; // 唯一标识符
    name: string; // 机箱名称
    imageUrl: string; // 图片链接
    price: number; // 价格
    linkUrl: string; // 购买链接
    brand: string; // 品牌
    type: string; // 类型
}
export function newDisplayVO(): DisplayVO {
    return {
        id: 0,
        name: '',
        imageUrl: '',
        price: 0,
        linkUrl: '',
        brand: '',
        type: ''
    }
}


//获取所有显示器
export const getAllDisplay = () => {
    return axios.get(`${DISPLAY_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}

//根据显示器Id获取显示器
export const getDisplayById = (displayId: number) => {
    return axios.get(`${DISPLAY_MODULE}/${displayId}`)
        .then(res => {
            return res.data.result;
        })
}