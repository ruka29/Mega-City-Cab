import { CommonModule } from '@angular/common';
import { Component, Input } from '@angular/core';
import { DefaultTabComponent } from '../default-tab/default-tab.component';
import { NewReservationTabComponent } from '../new-reservation-tab/new-reservation-tab.component';
import { ManageCustomersTabComponent } from '../manage-customers-tab/manage-customers-tab.component';
import { ManageReservationTabComponent } from '../manage-reservation-tab/manage-reservation-tab.component';
import { ProfileTabComponent } from '../profile-tab/profile-tab.component';

@Component({
  selector: 'app-dashboard-action-panel',
  standalone: true,
  imports: [
    CommonModule,
    DefaultTabComponent,
    NewReservationTabComponent,
    ManageCustomersTabComponent,
    ManageReservationTabComponent,
    ProfileTabComponent,
  ],
  templateUrl: './dashboard-action-panel.component.html',
  styleUrl: './dashboard-action-panel.component.scss',
})
export class DashboardActionPanelComponent {
  @Input() activeTab: string = '';
}
