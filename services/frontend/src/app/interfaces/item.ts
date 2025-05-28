export interface Item {
  uuid: string;
  vendorUuid: string;
  name: string;
  description: string;
  price: number;
  currency: string;
  quantity: number;
  rating: number;
}

export interface CreateItem {
  name: string;
  description: string;
  price: number;
  currency: string;
  quantity: number;
  tags: string[];
}
