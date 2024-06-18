import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: 'Dashboard',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/category',
    component: Layout,
    children: [
      {
        path: 'list',
        name: '类别管理',
        component: () => import('@/views/kpop/category/list'),
        meta: { title: '歌手类别管理', icon: 'form' }
      }
    ]
  },

  {
    path: '/singer',
    component: Layout,
    redirect: '/singer/list',
    name: '歌手信息管理',
    meta: { title: '歌手信息管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'list',
        name: '歌手列表',
        component: () => import('@/views/kpop/singer/list'),
        meta: { title: '歌手列表', icon: 'table' }
      },
      {
        path: 'save',
        name: '新增歌手',
        component: () => import('@/views/kpop/singer/save'),
        meta: { title: '新增歌手', icon: 'tree' }
      },
      {
        path: 'edit/:id',
        name: '编辑歌手',
        component: () => import('@/views/kpop/singer/save'),  // 这里只是借用这个页面 本质上的地址还是/edit
        meta: { title: '编辑歌手信息', icon: 'tree' },
        hidden: true            // 这个界面不对外展示 只是用来修改和跳转
      }
    ]
  },

  {
    path: '/song',
    component: Layout,
    redirect: '/song/list',
    name: '歌曲音译管理',
    meta: { title: '歌曲音译管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'list',
        name: '歌曲列表',
        component: () => import('@/views/kpop/song/list'),
        meta: { title: '歌曲列表', icon: 'table' }
      },
      {
        path: 'baseInfo',
        name: '新增歌曲',
        component: () => import('@/views/kpop/song/baseInfo'),
        meta: { title: '新增歌曲', icon: 'tree' }
      },
      {
        path: 'baseInfo/:id',
        name: '新增歌曲(修改)',
        component: () => import('@/views/kpop/song/baseInfo'),
        meta: { title: '新增歌曲', icon: 'tree' },
        hidden: true            // 这个界面不对外展示 只是用来修改和跳转
      },
      {
        path: 'transliteration/:id',
        name: '编辑音译文件',
        component: () => import('@/views/kpop/song/transliteration'),  // 这里只是借用这个页面 本质上的地址还是/edit
        meta: { title: '编辑音译文件', icon: 'tree' },
        hidden: true            // 这个界面不对外展示 只是用来修改和跳转
      },
      {
        path: 'publish/:id',
        name: '最终发布',
        component: () => import('@/views/kpop/song/publish'),  // 这里只是借用这个页面 本质上的地址还是/edit
        meta: { title: '最终发布', icon: 'tree' },
        hidden: true            // 这个界面不对外展示 只是用来修改和跳转
      },
      {
        path: 'makeTrans',
        name: '快速制作音译文本',
        component: () => import('@/views/kpop/song/makeTrans'),   // 给人使用的文本制作测试接口
        meta: { title: '快速制作音译文本', icon: 'tree' }
      },

    ]
  },

  {
    path: '/banner',
    component: Layout,
    redirect: '/banner/list',
    name: '轮播图管理',
    meta: { title: '轮播图管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'list',
        name: '轮播图列表',
        component: () => import('@/views/kpop/banner/list'),
        meta: { title: '轮播图列表', icon: 'table' }
      },
      {
        path: 'save',
        name: '新增轮播图',
        component: () => import('@/views/kpop/banner/save'),
        meta: { title: '新增轮播图', icon: 'tree' },
      },
      {
        path: 'save/:id',
        name: '修改轮播图',
        component: () => import('@/views/kpop/banner/save'),
        meta: { title: '修改轮播图', icon: 'tree' },
        hidden: true            // 这个界面不对外展示 只是用来修改和跳转
      }
      
    ]
  },

  {
    path: '/message',
    component: Layout,
    children: [
      {
        path: 'list',
        name: '消息管理',
        component: () => import('@/views/message/list'),
        meta: { title: '消息管理', icon: 'form' }
      }
    ]
  },

  {
    path: '/member',
    component: Layout,
    children: [
      {
        path: 'list',
        name: '用户管理',
        component: () => import('@/views/member/list'),
        meta: { title: '用户管理', icon: 'form' }
      }
    ]
  },

/*   {
    path: '/example',
    component: Layout,
    redirect: '/example/table',
    name: 'Example',
    meta: { title: 'Example', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'table',
        name: 'Table',
        component: () => import('@/views/table/index'),
        meta: { title: 'Table', icon: 'table' }
      },
      {
        path: 'tree',
        name: 'Tree',
        component: () => import('@/views/tree/index'),
        meta: { title: 'Tree', icon: 'tree' }
      }
    ]
  },

  {
    path: '/form',
    component: Layout,
    children: [
      {
        path: 'index',
        name: 'Form',
        component: () => import('@/views/form/index'),
        meta: { title: 'Form', icon: 'form' }
      }
    ]
  },

  {
    path: '/nested',
    component: Layout,
    redirect: '/nested/menu1',
    name: 'Nested',
    meta: {
      title: 'Nested',
      icon: 'nested'
    },
    children: [
      {
        path: 'menu1',
        component: () => import('@/views/nested/menu1/index'), // Parent router-view
        name: 'Menu1',
        meta: { title: 'Menu1' },
        children: [
          {
            path: 'menu1-1',
            component: () => import('@/views/nested/menu1/menu1-1'),
            name: 'Menu1-1',
            meta: { title: 'Menu1-1' }
          },
          {
            path: 'menu1-2',
            component: () => import('@/views/nested/menu1/menu1-2'),
            name: 'Menu1-2',
            meta: { title: 'Menu1-2' },
            children: [
              {
                path: 'menu1-2-1',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-1'),
                name: 'Menu1-2-1',
                meta: { title: 'Menu1-2-1' }
              },
              {
                path: 'menu1-2-2',
                component: () => import('@/views/nested/menu1/menu1-2/menu1-2-2'),
                name: 'Menu1-2-2',
                meta: { title: 'Menu1-2-2' }
              }
            ]
          },
          {
            path: 'menu1-3',
            component: () => import('@/views/nested/menu1/menu1-3'),
            name: 'Menu1-3',
            meta: { title: 'Menu1-3' }
          }
        ]
      },
      {
        path: 'menu2',
        component: () => import('@/views/nested/menu2/index'),
        name: 'Menu2',
        meta: { title: 'menu2' }
      }
    ]
  }, */

  // 外部链接 展示不设置 后续准备搞音译获取教程
/*   {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'https://panjiachen.github.io/vue-element-admin-site/#/',
        meta: { title: 'External Link', icon: 'link' }
      }
    ]
  }, */

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

// 0220修改
export const asyncRoutes  = [
{
  path: '/admin',
  component: Layout,
  children: [
    {
      path: 'list',
      name: '管理员名单',
      component: () => import('@/views/admin/list'),
      meta: { title: '管理员名单', icon: 'form',roles:['super_admin']}
    }
  ]
},
{
  path: '/singerFavourite',
  component: Layout,
  children: [
    {
      path: 'list',
      name: '推荐喜爱歌手',
      component: () => import('@/views/kpop/singerFavourite/list'),
      meta: { title: '推荐喜爱歌手', icon: 'form',roles:['super_admin']}
    }
  ],

}
];



const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
