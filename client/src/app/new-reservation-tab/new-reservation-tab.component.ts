import { CommonModule } from '@angular/common';
import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';

@Component({
  selector: 'app-new-reservation-tab',
  standalone: true,
  imports: [CommonModule, FormsModule],
  templateUrl: './new-reservation-tab.component.html',
  styleUrl: './new-reservation-tab.component.scss'
})
export class NewReservationTabComponent {
  schedule: boolean = false;
  currentDate: string = new Date().toISOString().split('T')[0];
  currentTime: string = new Date().toTimeString().split(' ')[0].slice(0, 5);

  setSchedule(event: Event) {
    event.preventDefault();
    this.schedule = !this.schedule;
  }
}
