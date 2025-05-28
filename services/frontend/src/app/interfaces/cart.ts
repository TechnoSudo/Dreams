import { Item } from './item';

export interface CartItem {
  item: Item;
  quantity: number;
}

export interface Cart {
  totalPrice: number;
  items: CartItem[];
}

export interface AddCartItem {
  itemUuid: string;
  quantity: number;
}
