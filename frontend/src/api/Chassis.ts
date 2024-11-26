import { axios } from '../utils/request';
import { CHASSIS_MODULE } from './_prefix';




export interface ChassisVO {
    id: number; // 唯一标识符
    imageUrl: string; // 图片链接
    linkUrl: string; // 购买链接
    name: string; // 机箱名称
    price: number; // 价格
    brand: string; // 品牌
    type: string; // 机箱类型
}
export function newChassisVO(): ChassisVO {
    return {
        id: 0, // 默认 ID
        name: '', // 默认空字符串
        imageUrl: '', // 默认空字符串
        price: 0, // 默认 0
        linkUrl: '', // 默认空字符串
        brand: '', // 默认空字符串
        type: '' // 默认空字符串
    };
}


//获取所有机箱
export const getAllChassis = () => {
    return axios.get(`${CHASSIS_MODULE}/all`, )
        .then(res => {
            return res.data.result;
        })
}

//根据机箱Id获取机箱
export const getChassisById = (chassisId: number) => {
    return axios.get(`${CHASSIS_MODULE}/${chassisId}`)
        .then(res => {
            return res.data.result;
        })
}

