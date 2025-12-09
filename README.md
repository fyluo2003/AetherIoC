# AetherIoC · Spring IoC 核心思想的迷你实现 🚀

AetherIoC 用少量代码还原 Spring IoC 容器的关键流程：包扫描、Bean 定义、实例化、`@Value` 赋值、`@Autowired` 按类型/按名称注入，并提供 Servlet 与简单业务代码演示。

## 🗂️ 模块地图
- `com.cloudfly03.myspring.*`：自研 IoC 核心  
  - 🏷️ 注解：`Component`、`Value`、`Autowired`、`Qualifier`  
  - 🛠️ 工具：`MyTools`（包扫描）、`BeanDefinition`（元数据）  
  - 🧠 容器：`MyAnnotationConfigApplicationContext`  
  - 📦 示例：`myspring.entity.Account`、`Order`
- `com.cloudfly03.factory.BeanFactory`：基于 properties 的最小工厂（单例缓存）
- `com.cloudfly03.dao/service/servlet`：演示层代码
- `com.cloudfly03.spring.*`：对比用的 Spring 注解示例

## 🧭 核心流程（MyAnnotationConfigApplicationContext）
1) 🔍 扫描：`MyTools.getClasses(pack)` 获取包下所有类。  
2) 🏗️ 注册：`@Component` 类封装为 `BeanDefinition`，beanName 默认类名首字母小写或注解 value。  
3) ⚙️ 实例化 + `@Value`：反射创建对象；有 `@Value` 时按字段类型简单转换并调用 setter。  
4) 🔗 依赖注入：  
   - `@Autowired + @Qualifier` → 按名称 setter 注入  
   - 仅 `@Autowired` → 按类型匹配后 setter 注入  
5) 📥 获取：`getBean("beanName")` 直接从内存容器取用。

示例（按名称优先，再按类型）：

```
66:105:src/main/java/com/cloudfly03/myspring/MyAnnotationConfigApplicationContext.java
public void autowireObject(Set<BeanDefinition> beanDefinitions) {
    if (qualifierAnnotation != null) { // byName
        Object bean = getBean(qualifierAnnotation.value());
        method.invoke(object, bean);
    } else { // byType
        if (ioc.get(beanName).getClass() == declaredField.getType()) {
            method.invoke(object, bean);
        }
    }
}
```

## ⚡ 快速体验
1) 构建：`mvn clean package`（war，JDK 8+）  
2) 运行：  
   - 纯 IoC：运行 `TestSpring.main`，扫描 `com.cloudfly03.myspring.entity`，打印注入完成的 `account`。  
   - Servlet：部署后访问 `HelloServlet`，经 `HelloServiceImpl` -> `BeanFactory` 返回字符串列表。

## ✨ 项目亮点
- 极简可读：用 ~200 行代码走完整个 IoC 生命周期。  
- 双轨示例：properties 工厂 + 注解 IoC，展示从手写工厂到注解驱动的演进。  
- 概念对齐：beanName 生成、单例缓存、按名/按类型注入策略与 Spring 思路一致。

## 🔮 可扩展方向
- 循环依赖处理（三级缓存）、构造器注入  
- 更丰富的类型转换与集合注入  
- Bean 生命周期回调（初始化、销毁）与作用域支持
