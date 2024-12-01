<script setup lang="ts">
import { ref, computed } from 'vue';
import {login} from '../api/User.ts';
import router from '../router/index'
import {ElMessage} from "element-plus";

const phone = ref('');
const password = ref('');


const hasphoneInput = computed(() => phone.value != '');
const hasPasswordInput = computed(() => password.value != '');

const loginDisabled = computed(() => {
    return !hasphoneInput.value || !hasPasswordInput.value;
});

function handleLogin() {
    console.log(phone.value)
    console.log(password.value)
    login(
        phone.value as string,
        password.value as string
    ).then(res => {

        if(res.data.code === "000"){
            ElMessage.success({
                message: "登录成功",
                type: "success",
                center: true,
            });
            console.log(res)

            sessionStorage.setItem('token', res.data.result)
            
            console.log(sessionStorage.getItem('token'))


            router.push

        }else{
          console.log(res)
            ElMessage.error({
                message: res.data.msg,
                type: "error",
                center: true,
            });

        }
    })
    
}
</script>

<template>
    <el-main class="main-frame bgimage">
        <el-card class="login-card">
        <div>
            <h1>登入您的账户</h1>
            <el-form>
            <el-form-item>
                <label>手机号</label>

                <el-input id="tel" type="text" v-model="phone"
                        placeholder="请输入手机号"/>
            </el-form-item>

            <el-form-item>
                <label for="password">密码</label>
                <el-input id="password" type="password" v-model="password"
                        required
                        placeholder="••••••••"/>
            </el-form-item>

            <span class="button-group">
                <el-button @click.prevent="handleLogin" :disabled="loginDisabled"
                            type="primary">登入</el-button>
                <router-link to="/register" v-slot="{navigate}">
                    <el-button @click="navigate">去注册</el-button>
                </router-link>
            </span>
            </el-form>
        </div>
        </el-card>
    </el-main>
</template>

<style scoped>
.main-frame {
  width: 100vw;
  height: 100vh;

  display: flex;
  align-items: center;
  justify-content: center;
}

.bgimage {
  background-image: url("../assets/images/login.jpg");
}

.login-card {
  width: 60%;
  padding: 10px;
}

.error-warn {
  color: red;
}

.error-warn-input {
  --el-input-focus-border-color: red;
}

.button-group {
  padding-top: 10px;
  display: flex;
  flex-direction: row;
  gap: 30px;
  align-items: center;
  justify-content: right;
}
</style>