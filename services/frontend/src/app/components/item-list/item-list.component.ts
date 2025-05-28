import { Component, OnInit } from '@angular/core';
import { ItemService } from '../../services/item.service';
import { Item } from '../../interfaces/item';
import { Router } from '@angular/router';
import {CurrencyPipe} from '@angular/common';

@Component({
  selector: 'app-item-list',
  templateUrl: './item-list.component.html',
  imports: [
    CurrencyPipe
  ],
  styleUrls: ['./item-list.component.css']
})
export class ItemListComponent implements OnInit {
  items: Item[] = [];
  isLoading = true;
  error: string | null = null;

  constructor(
    private itemService: ItemService,
    private router: Router
  ) { }

  ngOnInit(): void {
    this.loadItems();
  }

  loadItems(): void {
    this.isLoading = true;
    this.error = null;

    this.itemService.getItems().subscribe({
      next: (items) => {
        this.items = items;
        this.isLoading = false;
      },
      error: (err) => {
        this.error = 'Failed to load items. Please try again later.';
        this.isLoading = false;
        console.error('Error loading items:', err);
      }
    });
  }

  viewItemDetails(itemUuid: string): void {
    this.router.navigate(['/items', itemUuid]);
  }
}
