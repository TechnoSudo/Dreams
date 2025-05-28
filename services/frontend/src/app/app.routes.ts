import { Routes } from '@angular/router';

import { ItemListComponent } from './components/item-list/item-list.component';
import { ItemDetailComponent } from './components/item-detail/item-detail.component';
import { CartComponent } from './components/cart/cart.component';

export const routes: Routes = [
  {
    path: '',
    component: ItemListComponent,
    title: 'Dreams Store - Home'
  },
  {
    path: 'items/:uuid',
    component: ItemDetailComponent,
    title: 'Product Details'
  },
  {
    path: 'cart',
    component: CartComponent,
    title: 'Your Cart'
  },
  // Redirect unknown paths to home
  { path: '**', redirectTo: '' }
];
